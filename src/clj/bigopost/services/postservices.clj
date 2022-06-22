(ns bigopost.services.postservices 
  (:require [bigopost.db.core :as db]))

(defn get-people [_]
  (let [people (db/fetch-people!)]
    {:status 200  :body people}))

(defn get-person [{:keys [params]}]
  (if-let [person (db/get-person! params)]
    {:status 200 :body person}
    {:status 200 :body nil}))

(defn add-person [{:keys [params]}]
  (if (db/add-person! params)
    {:status 200 :body {"success?" true}}
    {:status 200 :body {"success?" false}}))

(defn delete-person [{{:keys [id]} :path-params}]
  (if (db/delete-person! {:id id})
    {:status 200 :header {"Content-type" "application/json"} :body {"message" "Deleted successfully"}}
    {:status 200 :body {:message false}}))