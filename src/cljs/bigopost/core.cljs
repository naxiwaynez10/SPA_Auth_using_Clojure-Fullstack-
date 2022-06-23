(ns bigopost.core
  (:require [ajax.core :refer [POST]]
            [bigopost.pages.aboutpage :refer [about-page]]
            [bigopost.pages.views :as views]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [reitit.coercion.schema :as rsc]
            [reitit.frontend :as rf]
            [reitit.frontend.controllers :as rfc]
            [reitit.frontend.easy :as rfe]
            [schema.core :as s]
            [fipp.edn :as fedn]))

(defonce state (r/atom {:loading? true}))

;; (defn item-page [match]
;;   (let [{:keys [path query]} (:parameters match)
;;         {:keys [id]} path]
;;     [:div
;;      [:ul
;;       [:li [:a {:href (rfe/href ::item {:id 1})} "Item 1"]]
;;       [:li [:a {:href (rfe/href ::item {:id 2} {:foo "bar"})} "Item 2"]]]
;;      (if id
;;        [:h2 "Selected item " id])
;;      (if (:foo query)
;;        [:p "Optional foo query param: " (:foo query)])]))


;;   (js/setTimeout #(login-done user) 5050)



(defn log-fn [& params]
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
       :controllers [{:start (log-fn "start" "frontpage controller")
                      :stop (log-fn "stop" "frontpage controller")}]}]
     ["/"
      {:name :dashboard-two
       :private? true
       :controllers [{:start (log-fn "start" "frontpage controller")
                      :stop (log-fn "stop" "frontpage controller")}]}]


     ["/about"
      {:name :about
       :view about-page
       :private? true}]

     ["/items"
      ;; Shared data for sub-routes
      {:private? true
       :controllers [{:start (log-fn "start" "items controller")
                      :stop (log-fn "stop" "items controller")}]}

      [""
       {:name :item-list
        :controllers [{:start (log-fn "start" "item-list controller")
                       :stop (log-fn "stop" "item-list controller")}]}]
      ["/:id"
       {:name :item
        :parameters {:path {:id s/Int}
                     :query {(s/optional-key :foo) s/Keyword}}
        :controllers [{:params (fn [match]
                                 (:path (:parameters match)))
                       :start (fn [params]
                                (js/console.log "start" "item controller" (:id params)))
                       :stop (fn [params]
                               (js/console.log "stop" "item controller" (:id params)))}]}]]]]
   {:data {:controllers [{:start (log-fn "start" "root-controller")
                          :stop (log-fn "stop" "root controller")}]
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
    (swap! state assoc :loading? false)))



  (POST "/api/get-auth-user"
    {:handler ajax-request})





(defn dashboard-view []
     
  [:<>
  ;;  [:pre (with-out-str (fedn/pprint @state))]
   [views/main-panel  @state]])





(defn ^:dev/after-load init! [] 
  (rfe/start!
   routes
   (fn [new-match]

     (swap! state (fn [state]
                    (if new-match
                       ;; Only run the controllers, which are likely to call authenticated APIs,
                       ;; if user has been authenticated.
                       ;; Alternative solution could be to always run controllers,
                       ;; check authentication status in each controller, or check authentication status in API calls.
                      (if (:identity state)
                        (assoc state :match (assoc new-match :controllers (rfc/apply-controllers (:controllers (:match state)) new-match)))
                        (assoc state :match new-match))))))
   {:use-fragment false})

  (rdom/render [dashboard-view] (.getElementById js/document "app")))

(init!)
