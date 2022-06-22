(ns bigopost.middleware
  (:require [bigopost.db.core :as db]
            [bigopost.env :refer [defaults]]
            [bigopost.layout :refer [error-page]]
            [bigopost.middleware.formats :as formats]
            [clojure.tools.logging :as log]
            [muuntaja.middleware :refer [wrap-format wrap-params]]
            [ring.adapter.undertow.middleware.session :refer [wrap-session]]
            [ring.middleware.anti-forgery :refer [wrap-anti-forgery]]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.flash :refer [wrap-flash]]))

(defn wrap-internal-error [handler]
  (fn [req]
    (try
      (handler req)
      (catch Throwable t
        (log/error t (.getMessage t))
        (error-page {:status 500
                     :title "Something very bad has happened!"
                     :message "We've dispatched a team of highly trained gnomes to take care of the problem."})))))

(defn wrap-csrf [handler]
  (wrap-anti-forgery
   handler
   {:error-response
    (error-page
     {:status 403
      :title "Invalid anti-forgery token"})}))


(defn wrap-formats [handler]
  (let [wrapped (-> handler wrap-params (wrap-format formats/instance))]
    (fn [request]
      ;; disable wrap-formats for websockets
      ;; since they're not compatible with this middleware
      ((if (:websocket? request) handler wrapped) request))))


;; Expires the session after a spwcific time
(defn wrap-expire-sessions [handler & [{:keys [inactive-timeout
                                               hard-timeout]
                                        :or {inactive-timeout (* 1000 60 15)
                                             hard-timeout (* 1000 60 60 2)}}]]
  (fn [req]
    (let [now (System/currentTimeMillis)
          session (:session req)
          session-key (:session/key req)]
      (if session-key ;; there is a session
        (let [{:keys [last-activity session-created]} session]
          (if (and last-activity
                   (< (- now last-activity) inactive-timeout)
                   session-created
                   (< (- now session-created) hard-timeout))
            (let [resp (handler req)]
              (if (:session resp)
                (-> resp
                    (assoc-in [:session :last-activity] now)
                    (assoc-in [:session :session-created] session-created))
                resp))
            ;; expired session
            ;; block request and delete session
            {:body "Your session has expired."
             :status 401
             :headers {}
             :session nil}))
        ;; no session, just call the handler
        (handler req)))))


(defn wrap-check-auth [handler]
   (fn [req]
    (let [auth-user (get-in req [:cookies "auth-user" :value])
          auth-id (get-in req [:cookies "auth-id" :value])
          user  (dissoc (db/get-logged-user {:id auth-user :authkey auth-id}) :pass)]
      (if (some? user)
        (handler (-> req (assoc :authenticated true  :identity user)))
        (handler (-> req
                     (dissoc [:authenticated])
                     (dissoc [:identity])
                     (dissoc [:cookies])))))))
                              

(defn wrap-base [handler]
  (-> ((:middleware defaults) handler)
      wrap-flash
      (wrap-session {:cookie-attrs {:http-only true}})
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)
           (dissoc :session)))
      wrap-internal-error))

(defn wrap-content-type [handler content-type]
  (fn [req]
    (-> req (assoc-in [:headers "Content-type"] content-type))
    (handler req)))