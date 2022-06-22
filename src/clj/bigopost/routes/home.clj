(ns bigopost.routes.home
  (:require
   [bigopost.layout :as layout] ;;  [clojure.java.io :as io]
   [bigopost.middleware :as middleware]
   [bigopost.services.authservices :refer [login logout register
                                           user-data]]
   [bigopost.services.postservices :refer [get-people add-person delete-person
                                           get-person]]
   [ring.util.http-response :as response]
   [ring.util.response]))

(defn home-page [request]
  (layout/render request "home.html"))



(defn home-routes []

   [""
    {:middleware [middleware/wrap-check-auth
                  middleware/wrap-formats]}
    ["/api"
    ;; authentications
     ["/get-auth-user" {:post user-data}]
     

    ;; POSTS
     ["/people/get" {:post get-people}]
     ["/person/get/:id" {:post get-person}]
     ["/person/delete/:id" {:post delete-person}]
     ["/person/add" {:post add-person}]]
    ["/" (fn [_]
           (response/found "/dashboard"))]
    ["/login" {:get home-page
               :post login}]
    ["/register" {:get home-page
                  :post register}]
    
    ["/logout" {:post logout}]
    ["/dashboard"
     [""
      {:get home-page}]
     ["/*path" {:get home-page}]]])

