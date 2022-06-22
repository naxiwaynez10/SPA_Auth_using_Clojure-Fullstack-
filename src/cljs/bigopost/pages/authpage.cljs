(ns bigopost.pages.authpage
  (:require [ajax.core :refer [POST]]
            [clojure.string :as str]
            [reagent.core :as r]
            [reitit.frontend.easy :as rfe]))


(defonce state (r/atom {}))
;; (set! (.-location js/window) "/dashboard")
(defn login-controller [params]
  (POST "/login"
    {:handler (fn [response]
                (let [no-access? (get-in response ["access"])
                      message (get-in response ["message"])]
                  (if (or (some? message) no-access?)
                    (swap! state assoc :form-error message)
                    (set! (.-location js/window) "/dashboard"))))
     :params params}))



(defn register [params]
  (POST "/register"
    {:handler (fn [response]
                (let [no-access? (get-in response ["access"])
                      message (get-in response ["message"])]
                  (if (or (some? message) no-access?)
                    (swap! state assoc :reg-error message :c_pass-error "" :pass-error "")
                    (set! (.-location js/window) "/dashboard"))))
     :params params}))

(defn register-controller [params]
  (swap! state assoc :reg-error "")
  (let [pc (count (str/split (:pass @state) ""))
        c_pc (count (str/split (:c_pass @state) ""))]
    (if (< pc 6)
      (swap! state assoc :pass-error "Password must be atleast 6 characters.")
      (if (and (some? c_pc) (not= (:pass @state) (:c_pass @state)))
        (swap! state assoc :c_pass-error "Passwords dosen't match." :pass-error "")
        {(swap! state assoc :c_pass-error "")
         (register params)}))))

(defn login-page []

  [:div.my-auto.page.page-h
   [:div.main-signin-wrapper
    [:div.main-card-signin.d-md-flex.wd-100p
     [:div.wd-md-50p.login.d-none.d-md-block.page-signin-style.p-5.text-white
      [:div.my-auto.authentication-pages
       [:div
        [:img.m-0.mb-4
         {:alt "logo" :src "/assets/img/brand/logo-white.png"}]
        [:h5.mb-4 "Responsive Modern Dashboard & Admin Template"]
        [:p.mb-5
         "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book."]
        [:a.btn.btn-danger {:href "/"} "Learn More"]]]]
     [:div.p-5.wd-md-50p
      [:div.main-signin-header
       [:h2 "Welcome back!"]
       [:h4 "Please sign in to continue"]
       [:span.text-danger (:form-error @state)]
       [:form
        {:on-submit (fn [e]
                      (.preventDefault e)
                      (login-controller @state))}
        [:div.form-group
         [:label "Email"]
         [:input.form-control
          {:value (:email @state)
           :required true
           :on-change #(swap! state assoc :email (-> % .-target .-value))
           :type "text"
           :placeholder "Enter your email"}]]
        [:div.form-group
         [:label "Password"]
         " "
         [:input.form-control
          {:value (:pass @state)
           :required true
           :on-change #(swap! state assoc :pass (-> % .-target .-value))
           :type "password"
           :placeholder "Enter your password"}]]
        [:button.btn.btn-main-primary.btn-block
         {:type "submit"}
         "Sign In"]]]
      [:div.main-signin-footer.mt-3.mg-t-5
       [:p [:a {:href ""} "Forgot password?"]]
       [:p
        "Don't have an account? "
        [:a {:href (rfe/href :register)} "Create an Account"]]]]]]])

(defn register-page []
  [:div.my-auto.page.page-h
   [:div.main-signin-wrapper
    [:div.main-card-signin.d-md-flex.wd-100p
     [:div.wd-md-50p.login.d-none.d-md-block.page-signin-style.p-5.text-white
      [:div.my-auto.authentication-pages
       [:div
        [:img.m-0.mb-4
         {:alt "logo" :src "/assets/img/brand/logo-white.png"}]
        [:h5.mb-4 "Responsive Modern Dashboard & Admin Template"]
        [:p.mb-5
         "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries but also the leap into electronic typesetting"]
        [:a.btn.btn-danger {:href "index.html"} "Learn More"]]]]
     [:div.sign-up-body.wd-md-50p
      [:div.main-signin-header
       [:h2 "Welcome back!"]
       [:h4 "Please Register withXino"]
       [:span.text-danger (:reg-error @state)]
       [:form
        {:on-submit (fn [e]
                      (.preventDefault e)
                      (register-controller @state))}
        [:div.form-group
         [:label "Firstname"]
         " "
         [:input.form-control
          {:type "text"
           :value (:first_name @state)
           :on-change #(swap! state assoc :first_name (-> % .-target .-value))
           :required true
           :placeholder "Enter your firstname"}]]
        [:div.form-group
         [:label "Lastname"]
         " "
         [:input.form-control
          {:type "text"
           :value (:last_name @state)
           :on-change #(swap! state assoc :last_name (-> % .-target .-value))
           :required true
           :placeholder "Enter your lastname"}]]
        [:div.form-group
         [:label "Email"]
         " "
         [:input.form-control
          {:type "text"
          :value (:email @state) 
           :on-change #(swap! state assoc :email (-> % .-target .-value))
           :required true
           :placeholder "Enter your email"}]
         [:span.text-danger (:reg-error @state)]]
        
        [:div.form-group
         [:label "Password"]
         " "
         [:input.form-control
          {:type "password"
           :value (:pass @state)
           :required true
           :on-change #(swap! state assoc :pass (-> % .-target .-value))
           :placeholder "Enter your password"}]
         [:span.text-danger (:pass-error @state)]]
        
        [:div.form-group
         [:label "Confirm Password"]
         " "
         [:input.form-control
          {:type "password" 
           :value (:c_pass @state)
           :on-change #(swap! state assoc :c_pass (-> % .-target .-value))
           :placeholder "Confirm your password"}]
         [:span.text-danger (:c_pass-error @state)]]

        [:button.btn.btn-main-primary.btn-block
        {:type "submit"}
         "Create Account"]]]
      [:div.main-signup-footer.mg-t-10
       [:p
        "Already have an account? "
        [:a {:href (rfe/href :login)} "Sign In"]]]]]]])