(ns bigopost.pages.todos
  (:require [reagent.core :as r]
            [clojure.string :as str]
            [ajax.core :refer [GET]]))


(defonce api (r/atom {:sort :id :dir true}))

(defn get-api []
  (GET "https://jsonplaceholder.typicode.com/todos/"
  {:handler (fn [res]
              (swap! api assoc :data res))
   :response-format :json
   :keywords? true}))

(get-api)



(defn sort-api []
  (if (:dir @api)
    (sort-by (:sort @api) > (:data @api))
  (sort-by (:sort @api) < (:data @api))))

(defn class []
  (if (:dir @api) (str "fa-arrow-up") (str "fa-arrow-down")))

(defn active [ref]
  (if (= (:sort @api) ref) (str "active") (str "")))

(defn tr [item]
  (let [id (:id item)
                 userId (:userId item)
                 title (:title item)
                 completed (:completed item)]
             [:tr {:key id}
              [:th {:scope "row"} id]
              [:td userId]
              [:td title]
              [:td (str completed)]]))

(defn todos []
  [:div.card.mt-5
   [:div.card-heading.text-center.p-5 [:h2 "List users"]
    [:div.form-group
     [:label {:for "sort"} "Search Title"]
     [:input.form-control {:value (:title @api)
                           :on-change #(swap! api assoc :title (-> % .-target .-value))
                           :placeholder "Search for title here.."}]]]

   [:div.card-body
    [:table {:class ".mt-5 table"}
     [:caption "List of users"]
     [:thead
      [:tr
       [:th {:scope "col"} "#"]
       [:th {:scope "col"
             :class "active"
          ;;  (active :userId)
             :on-click #(swap! api assoc :sort :userId :dir (not (:dir @api)))} "userId  " [:i.fa {:class (class)}]]
       [:th {:scope "col"
             :class (active :title)
             :on-click #(swap! api assoc :sort :title :dir (not (:dir @api)))} "Title  " [:i.fa {:class (class)}]]
       [:th {:scope "col"
             :class (active :completed)
             :on-click #(swap! api assoc :sort :completed :dir (not (:dir @api)))} "completed?  " [:i.fa {:class (class)}]]]]
     [:tbody
      (map (fn [item]
             (print (str (:title item)))
             (if-not (empty? (:title @api))
               (if (str/includes? (str (:title item)) (str (:title @api))) (tr item))
               (tr item)))  (sort-api))]]]])


