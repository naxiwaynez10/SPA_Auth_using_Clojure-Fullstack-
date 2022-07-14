(ns bigopost.core
  (:require [ajax.core :refer [POST GET]]
            [bigopost.pages.views :as views]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reitit.coercion.schema :as rsc]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]))

(defonce state (r/atom {:loading? true}))

(defn log
  "just a log to Console function"
  [& params]
  (fn [_]
    (apply js/console.log params)))


(def routes
  (rf/router
   [["/login" {:name :login}]
    ["/register" {:name :register}]
    ["/dashboard"
     [""
      {:name :dashboard-one
       :private? true
       :controllers [{:start (log "start" "frontpage controller")
                      :stop (log "stop" "frontpage controller")}]}]
     ["/"
      {:name :dashboard-two
       :private? true
       :controllers [{:start (log "start" "frontpage controller")
                      :stop (log "stop" "frontpage controller")}]}]


     ["/about"
      {:name :about
       :private? true}]]]
   {:data {:controllers [{:start (log "start" "root-controller")
                          :stop (log "stop" "root controller")}]
           :coercion rsc/coercion
           :public? false}}))



(defn ajax-request [response]
  (let [authenticated? (get-in response ["authenticated?"])
        identity (get-in response ["identity"])]
    (if authenticated?
      (if (= (-> @state :match :data :name) (or :login :register))
        (rfe/replace-state :dashboard-one))
      (if (-> @state :match :data :private?)
        (rfe/replace-state :login)))
    (swap! state assoc :loading? false :identity identity :authenticated? authenticated?)))


   
  (POST "/api/get-auth-user"
    {:handler ajax-request})





(defn dashboard-view []
     
  [:<>
  ;;  [:pre (with-out-str (fedn/pprint @state))]
   [views/main-panel  @state]])



(log @state)

(defn ^:dev/after-load init! []
  (rfe/start!
   routes
   (fn [new-match]

     (swap! state (fn [state]
                    (if new-match
                      (assoc state :match new-match)))))
   {:use-fragment false})

  (rdom/render [dashboard-view] (.getElementById js/document "app")))

(init!)
