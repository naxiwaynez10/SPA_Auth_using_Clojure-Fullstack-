(ns bigopost.components.breadcrumb)


(defn main []
 [:div.breadcrumb-header.justify-content-between
    [:div.my-auto
     [:div.d-flex
      [:h4.content-title.mb-0.my-auto "Pages"]
      [:span.text-muted.mt-1.tx-13.ml-2.mb-0 "/ Empty"]]]
    [:div.d-flex.align-items-end.flex-wrap.my-auto.right-content.breadcrumb-right
    ;;  [:button.btn.btn-warning.btn-icon.mr-3.mt-2.mt-xl-0
    ;;   {:type "button"}
    ;;   [:i.mdi.mdi-download]]
    ;;  [:button.btn.btn-danger.btn-icon.mr-3.mt-2.mt-xl-0
    ;;   {:type "button"}
    ;;   [:i.mdi.mdi-clock]]
    ;;  [:button.btn.btn-success.btn-icon.mr-3.mt-2.mt-xl-0 "Add a contact "
    ;;   {:type "button" :data-toggle "modal" :data-target "#modaldemo3"}
    ;;    [:i.mdi.mdi-plus]]
     [:button.btn.btn-success.mt-2.mt-xl-0
     {:data-toggle "modal" :data-target "#modaldemo3"}
      [:i.mdi.mdi-plus]
      "  Add a contact"]]])