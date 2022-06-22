(ns bigopost.services.authservices
  (:require [bigopost.db.core :as db]
            [buddy.hashers :as bh]
            [clj-time.core :as t]
            [ring.util.http-response :as response]))



(defn UUID []  (java.util.UUID/randomUUID))

(defn check-user-details [credentials & args]
  (if (or (empty? (:email credentials)) (empty? (:pass credentials)))
    [false false {:message "Username or password cannot be empty"}]
    (let [user (db/get-user {:id (:email credentials)})
          email (:email user)
          db-pass (:pass user)]
      (if (some? email) ;; check if email exists
        (if (some? args)
          [false true {:message "Email already taken"}]
          (if (bh/check (:pass credentials) db-pass)
          ;; check if the hash matches
            [true true {:user (dissoc user :pass)}] ;;Return the user details after removing the password
            [false false {:message "Wrong username or password"}]))
        [false false {:message "Wrong username or password"}]))))



(defn login
  
  "Logs the user in"
  
  [request]
  
  (let [[user-exist? _ user] (check-user-details (:params request))]
    (if user-exist?
      (do   (-> request (assoc-in [:cookies "auth-user"] {:value (-> user :user :id) :secure false :path "/" :http-only true})
                (assoc-in [:cookies "auth-id"] {:value (-> user :user :authkey) :secure false :path "/" :http-only true})
                (assoc :body {"access" false} :headers {"Content-type" "application/json"})))
      {:status 200 :headers {"Content-type" "application/json"} :body {"access" true "message" (:message user)}})))

(defn logout
  "Logout the User in"
  [request]
  (-> request (assoc-in [:cookies "auth-user"] {:value "" :max-age -1})
      (assoc-in [:cookies "auth-id"] {:value "" :max-age -1})
      (assoc :status 200 :body {})))


(defn register 
"Register a new user"
[request]
  (let [params (:params request)
        [_ user-exist? message] (check-user-details params 1)
        authkey (.toString (UUID))
        ph (bh/encrypt (:pass params))
        req (dissoc params :pass)
        res (assoc req :authkey authkey :pass ph :date_joined (t/now))]
    (if user-exist?
      {:status 200 :header {"Content-type" "application/json"} :body {"access" true "message" (:message message)}}
      (if (db/add-user! res)
        (login request)))))



(defn user-data [{:keys [authenticated identity] :or {authenticated false}}]
  {:status 200   :body {"authenticated?" authenticated "identity" identity}})
