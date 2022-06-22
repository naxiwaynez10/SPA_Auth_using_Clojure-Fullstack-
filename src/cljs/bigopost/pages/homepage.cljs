(ns bigopost.pages.homepage 
  (:require [ajax.core :refer [POST]]
            [reagent.core :as r]))




(defonce state (r/atom {}))
(defonce form (r/atom {}))

(defn get-people []
  (POST "/api/people/get"
     {:handler (fn [res]
                (reset! state res))}))

(defn save-person! [params]
  (POST "/api/person/add"
    {:handler (fn [res]
                (if (get-in res ["success?"])
                  (do (reset! form {})
                      (swap! form assoc :response "Contact added successfully")
                      (get-people))))
     :params params}))

(defn delete [id]
  (POST (str "/api/person/delete/" id) 
    {:handler (fn [res] 
                (if (some? (get-in res ["message"])) 
                  (get-people)))}))

(get-people)


(defn add-person-modal []

    [:div#modaldemo3.modal
     [:div.modal-dialog
      {:role "document"}
      [:div.modal-content.modal-content-demo
       [:div.modal-header
        [:h6.modal-title "Add a contact"]
        [:button.close
         {:type "button" :data-dismiss "modal" :aria-label "Close"}
         [:span {:aria-hidden "true"} "×"]]]
       [:div.modal-body
        [:div.col-lg-12
         [:div.main-signin-header
          (if (:response @form) [:div.alert.alert-success (:response @form)])
          [:form
           [:div.form-group
            [:label "Full name"]
            " "
            [:input.form-control
             {:value (:full_name @form)
              :placeholder "Enter Full Name"
              :required true
              :on-change #(swap! form assoc :full_name (-> % .-target .-value))
              :type "text"}]]
           [:div.form-group
            [:label "Role"]
            " "
            [:select.form-control
             {:on-change #(swap! form assoc :role (-> % .-target .-value))}
             [:option (:role @form)]
             [:option "Employee"]
             [:option "Project Manager"]
             [:option "C.E.O"]]]
           [:div.form-group
            [:label "Email"]
            " "
            [:input.form-control
             {:value (:email @form)
              :on-change #(swap! form assoc :email (-> % .-target .-value))
              :placeholder "Enter Email"
              :required ""
              :type "email"}]
            [:span.text-danger]]
           [:div.form-group
            [:div.input-group
             [:div.input-group-prepend
              [:div.input-group-text
               "Phone:"]]
             [:input#phoneMask.form-control
              {:type "text" :value (:phone @form) :placeholder "(234) 000-0000" :on-change #(swap! form assoc :phone (-> % .-target .-value))}]]]
           [:div.form-group
            [:label "Address"]
            " "
            [:input.form-control
             {:value (:address @form)
              :on-change #(swap! form assoc :address (-> % .-target .-value))
              :placeholder "State/Province, Country"
              :required ""
              :type "email"}]
            [:span.text-danger]]
           [:div.form-group
            [:textarea.form-control {:rows "3" :placeholder "Brief About user" :on-change #(swap! form assoc :about (-> % .-target .-value))} (:about @form)]]]]]]
       [:div.modal-footer
        [:button.btn.ripple.btn-primary {:type "button" :on-click #(save-person! @form)} "Save"]
        [:button.btn.ripple.btn-secondary
         {:type "button" :data-dismiss "modal"}
         "Close"]]]]])



(defn home-page []
[:<>
 (map (fn [state]
        [:div.col-sm-12.col-md-6.col-lg-6.col-xl-3
         {:key (:id state)}
         [:div.card.custom-card
          [:div.card-body.text-center
           [:div.user-lock.text-center
            [:div.dropdown.text-right
             [:a
              {:aria-expanded "true"
               :aria-haspopup "true"
               :data-toggle "dropdown"
               :href "#"}
              [:i.fe.fe-more-vertical.text-dark.fs-16]]
             [:div.dropdown-menu.dropdown-menu-right.shadow
              [:a.dropdown-item
               {:href "#"}
               [:i.fe.fe-message-square.mr-2]
               " Message"]
              [:a.dropdown-item {:href "#"} [:i.fe.fe-edit-2.mr-2] " Edit"]
              [:a.dropdown-item {:href "#"} [:i.fe.fe-eye.mr-2] " View"]
              [:a.dropdown-item
               {:href "#"
                :on-click #(delete (:id state))}
               [:i.fe.fe-trash-2.mr-2]
               " Delete"]]]
            [:img.rounded-circle
             {:src "/assets/img/faces/2.jpg" :alt "avatar"}]]
           [:h5.mb-1.mt-3.card-title (:full_name state)]
           [:p.mb-2.mt-1.tx-inverse (:role state)]
           [:p.text-muted.text-center.mt-0
            (:about state)]
           [:div.mt-2.user-info.btn-list
            [:a.btn.btn-outline-light.btn-block
             {:href ""}
             [:i.fe.fe-mail.mr-2]
             [:span (:email state)]]
            [:a.btn.btn-outline-light.btn-block
             {:href ""}
             [:i.fe.fe-phone.mr-2]
             [:span (:phone state)]]
            [:a.btn.btn-outline-light.btn-block
             {:href ""}
             [:i.fe.fe-map-pin.mr-2]
             [:span (:address state)]]]]]]) @state)
      
      [add-person-modal]])