(ns bigopost.components.top-menu 
  (:require
   [ajax.core :refer [POST]]))


(defn logout []
  (POST "/logout"
    {:handler (fn [_]
                (set! (.-location js/window) "/login"))}))

(defn main-header-left []
  [:div.main-header-left
    [:div.app-sidebar__toggle.mobile-toggle
     {:data-toggle "sidebar"}
     [:a.open-toggle
      {:href "#"}
      [:svg.eva.eva-menu-outline.header-icons
       {:viewBox "0 0 24 24" :height "24" :width "24"}
       [:g
        {:data-name "Layer 2"}
        [:g
         {:data-name "menu"}
         [:rect
          {:opacity "0"
           :transform "rotate(180 12 12)"
           :height "24"
           :width "24"}]
         [:rect
          {:ry ".95"
           :rx ".95"
           :height "2"
           :width "18"
           :y "11"
           :x "3"}]
         [:rect
          {:ry ".95"
           :rx ".95"
           :height "2"
           :width "18"
           :y "16"
           :x "3"}]
         [:rect
          {:ry ".95"
           :rx ".95"
           :height "2"
           :width "18"
           :y "6"
           :x "3"}]]]]]
     [:a.close-toggle
      {:href "#"}
      [:svg.eva.eva-close-outline.header-icons
       {:viewBox "0 0 24 24" :height "24" :width "24"}
       [:g
        {:data-name "Layer 2"}
        [:g
         {:data-name "close"}
         [:rect
          {:opacity "0"
           :transform "rotate(180 12 12)"
           :height "24"
           :width "24"}]
         [:path
          {:d
           "M13.41 12l4.3-4.29a1 1 0 1 0-1.42-1.42L12 10.59l-4.29-4.3a1 1 0 0 0-1.42 1.42l4.3 4.29-4.3 4.29a1 1 0 0 0 0 1.42 1 1 0 0 0 1.42 0l4.29-4.3 4.29 4.3a1 1 0 0 0 1.42 0 1 1 0 0 0 0-1.42z"}]]]]]]
    [:div.main-header-center.ml-3.d-sm-none.d-md-none.d-lg-block
     [:input.form-control
      {:placeholder "Search for anything..." :type "search"}]
     [:button.btn [:i.fas.fa-search]]]])


(defn main-header-center []
  [:div.main-header-center
    [:div.responsive-logo
     [:a
      {:href "index.html"}
      [:img.mobile-logo
       {:alt "logo" :src "/assets/img/brand/logo.png"}]]]])

(defn dropdown-messages []
  [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
   [:div.main-dropdown-header.d-sm-none
    [:a.main-header-arrow {:href ""} [:i.icon.ion-md-arrow-back]]]
   [:div.menu-header-content.text-left.d-flex
    [:div [:h6.menu-header-title.text-white.mb-0 "5 new Messages"]]
    [:div.my-auto.ml-auto
     [:span.badge.badge-pill.badge-warning.float-right
      "Mark All Read"]]]
   [:div.main-message-list.chat-scroll.ps.ps--active-y
    [:a.p-3.d-flex.border-bottom {:href "#"}
     [:div.drop-img.cover-image
      {:style
       {:background "rgba(0, 0, 0, 0) url(\"/assets/img/faces/5.jpg\") repeat scroll center center"},
       :data-image-src "/assets/img/faces/5.jpg"}
      [:span.avatar-status.bg-teal]]
     [:div.wd-90p
      [:div.d-flex
       [:h5.mb-1.name "Petey Cruiser"]
       [:p.time.mb-0.text-right.ml-auto.float-right "35 min ago"]]
      [:p.mb-0.desc "I'm sorry but i'm not sure how..."]]]
    [:div.ps__rail-x
     {:style {:left "0px" :top "0px"}}
     [:div.ps__thumb-x
      {:style {:left "0px" :width "0px"} :tabIndex "0"}]]
    [:div.ps__rail-y
     {:style {:top "0px" :right "0px" :height "219px"}}
     [:div.ps__thumb-y
      {:style {:top "0px" :height "142px"} :tabIndex "0"}]]

    [:div.text-center.dropdown-footer
     [:a {:href "text-center"} "VIEW ALL"]]]])

(defn dropdown-notifications []
  [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
   [:div.menu-header-content.text-left.d-flex
    [:div
     [:h6.menu-header-title.text-white.mb-0 "7 new Notifications"]]
    [:div.my-auto.ml-auto
     [:span.badge.badge-pill.badge-warning.float-right
      "Mark All Read"]]]
   [:div.main-notification-list.Notification-scroll.ps
    [:a.d-flex.p-3.border-bottom {:href "#"}
     [:div.notifyimg.bg-purple-transparent [:i.la.la-gem.text-purple]]
     [:div.ml-3
      [:h5.notification-label.mb-1 "Updates Available"]
      [:div.notification-subtext "2 days ago"]]
     [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]]
    [:div.ps__rail-x
     {:style {:left "0px" :top "0px"}}
     [:div.ps__thumb-x
      {:style {:left "0px" :width "0px"} :tabIndex "0"}]]
    [:div.ps__rail-y
     {:style {:top "0px" :right "0px" :height "219px"}}
     [:div.ps__thumb-y
      {:style {:top "0px" :height "142px"} :tabIndex "0"}]]]
   [:div.dropdown-footer [:a {:href ""} "VIEW ALL"]]])   

(defn profile-dropdown []
  [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
    [:div.main-header-profile.header-img
     [:div.main-img-user
      [:img {:src "/assets/img/faces/5.jpg", :alt ""}]]
     [:h6 "Elizabeth Jane"]
     [:span "Premium Member"]]
    [:a.dropdown-item {:href ""} [:i.far.fa-user] " My Profile"]
    [:a.dropdown-item {:href ""} [:i.far.fa-edit] " Edit Profile"]
    [:a.dropdown-item {:href ""} [:i.far.fa-clock] " Activity Logs"]
    [:a.dropdown-item
     {:href ""}
     [:i.fas.fa-sliders-h]
     " Account Settings"]
    [:a.dropdown-item
     {:href "#"
      :on-click #(logout)}
     [:i.fas.fa-sign-out-alt]
     " Sign Out"]])


(defn main-header-right []
  [:div.main-header-right
    [:div.nav.nav-item.navbar-nav-right.ml-auto
     [:form.navbar-form.nav-item.my-auto.d-lg-none
      {:role "search"}
      [:div.input-group.nav-item.my-auto
       [:input.form-control {:placeholder "Search" :type "text"}]
       [:span.input-group-btn
        [:button.btn.btn-default {:type "reset"} [:i.ti-close]]
        [:button.btn.btn-default.nav-link [:i.ti-search]]]]]
     [:div.nav-item.full-screen.fullscreen-button
      [:a.new.nav-link.full-screen-link
       {:href "#"}
       [:i.ti-fullscreen]]]
     [:div.dropdown.nav-item.main-header-message
      [:a.new.nav-link {:href "#"} [:i.ti-email] [:span.pulse-danger]]
      [dropdown-messages]]
     [:div.dropdown.nav-item.main-header-notification
      [:a.new.nav-link
       {:href "#"}
       [:i.ti-bell.animated.bell-animations]
       [:span.pulse]]
      [dropdown-notifications]]


     [:button.navbar-toggler.navresponsive-toggler.d-sm-none
      {:aria-label "Toggle navigation"
       :aria-expanded "false"
       :aria-controls "navbarSupportedContent-4"
       :data-target "#navbarSupportedContent-4"
       :data-toggle "collapse"
       :type "button"}
      [:span.navbar-toggler-icon.fe.fe-more-vertical]]



     [:div.dropdown.main-profile-menu.nav.nav-item.nav-link
      [:a.profile-user
       {:href ""}
       [:img {:src "/assets/img/faces/5.jpg" :alt ""}]]
      [profile-dropdown]]
     [:div.dropdown.main-header-message.right-toggle
      [:a.nav-link
       {:data-target ".sidebar-right" :data-toggle "sidebar-right"}
       [:i.ti-menu.tx-20.bg-transparent]]]]])

(defn responsive-header []
  [:div.responsive.main-header
   [:div.mb-1.navbar.navbar-expand-lg.nav.nav-item.navbar-nav-right.responsive-navbar.navbar-dark.d-sm-none
    [:div#navbarSupportedContent-4.collapse.navbar-collapse
     [:div.d-flex.order-lg-2.ml-auto
      [:form.navbar-form.nav-item.my-auto.d-lg-none
       {:role "search"}
       [:div.input-group.nav-item.my-auto
        [:input.form-control {:placeholder "Search" :type "text"}]
        [:span.input-group-btn
         [:button.btn.btn-default {:type "reset"} [:i.ti-close]]
         [:button.btn.btn-default.nav-link [:i.ti-search]]]]]
      [:div.d-md-flex
       [:div.nav-item.full-screen.fullscreen-button
        [:a.new.nav-link.full-screen-link
         {:href "#"}
         [:i.ti-fullscreen]]]]
      [:div.dropdown.nav-item.main-header-message.header-contact
       [:a.new.nav-link
        {:href "#"}
        [:i.ti-email]
        [:span.pulse-danger]]
       [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
        [:div.main-dropdown-header.d-sm-none
         [:a.main-header-arrow
          {:href ""}
          [:i.icon.ion-md-arrow-back]]]
        [:div.menu-header-content.text-left.d-flex
         [:div
          [:h6.menu-header-title.text-white.mb-0 "5 new Messages"]]
         [:div.my-auto.ml-auto
          [:span.badge.badge-pill.badge-warning.float-right
           "Mark All Read"]]]
        [:div.main-message-list.text-scroll.ps
         [:a.p-3.d-flex.border-bottom {:href "#"}]
         [:div.drop-img.cover-image
          {:style
           {:background "rgba(0 0 0 0) url(\"/assets/img/faces/3.jpg\") repeat scroll center center"}
           :data-image-src "/assets/img/faces/3.jpg"}
          [:span.avatar-status.bg-teal]]
         [:div.wd-90p
          [:div.d-flex
           [:h5.mb-1.name "Paul Molive"]
           [:p.time.mb-0.text-right.ml-auto.float-right "10 min ago"]]
          [:p.mb-0.desc "I'm sorry but i'm not sure how..."]]
         [:a.p-3.d-flex.border-bottom {:href "#"}]
         [:div.drop-img.cover-image
          {:style
           {:background "rgba(0 0 0 0) url(\"/assets/img/faces/2.jpg\") repeat scroll center center"}
           :data-image-src "/assets/img/faces/2.jpg"}
          [:span.avatar-status.bg-teal]]
         [:div.wd-90p
          [:div.d-flex
           [:h5.mb-1.name "Sahar Dary"]
           [:p.time.mb-0.text-right.ml-auto.float-right "13 min ago"]]
          [:p.mb-0.desc
           "All set ! Now time to get to you now......"]]
         [:a.p-3.d-flex.border-bottom {:href "#"}]
         [:div.drop-img.cover-image
          {:style
           {:background "rgba(0 0 0 0) url(\"/assets/img/faces/9.jpg\") repeat scroll center center"}
           :data-image-src "/assets/img/faces/9.jpg"}
          [:span.avatar-status.bg-teal]]
         [:div.wd-90p
          [:div.d-flex
           [:h5.mb-1.name "Khadija Mehr"]
           [:p.time.mb-0.text-right.ml-auto.float-right "20 min ago"]]
          [:p.mb-0.desc "Are you ready to pickup your Delivery..."]]
         [:a.p-3.d-flex.border-bottom {:href "#"}]
         [:div.drop-img.cover-image
          {:style
           {:background "rgba(0 0 0 0) url(\"/assets/img/faces/12.jpg\") repeat scroll center center"}
           :data-image-src "/assets/img/faces/12.jpg"}
          [:span.avatar-status.bg-danger]]
         [:div.wd-90p
          [:div.d-flex
           [:h5.mb-1.name "Barney Cull"]
           [:p.time.mb-0.text-right.ml-auto.float-right "30 min ago"]]
          [:p.mb-0.desc "Here are some products ..."]]
         [:a.p-3.d-flex.border-bottom {:href "#"}]
         [:div.drop-img.cover-image
          {:style
           {:background "rgba(0 0 0 0) url(\"/assets/img/faces/5.jpg\") repeat scroll center center"}
           :data-image-src "/assets/img/faces/5.jpg"}
          [:span.avatar-status.bg-teal]]
         [:div.wd-90p
          [:div.d-flex
           [:h5.mb-1.name "Petey Cruiser"]
           [:p.time.mb-0.text-right.ml-auto.float-right "35 min ago"]]
          [:p.mb-0.desc "I'm sorry but i'm not sure how..."]]
         [:div.ps__rail-x
          {:style {:left "0px" :top "0px"}}
          [:div.ps__thumb-x
           {:style {:left "0px" :width "0px"} :tabIndex "0"}]]
         [:div.ps__rail-y
          {:style {:top "0px" :right "0px"}}
          [:div.ps__thumb-y
           {:style {:top "0px" :height "0px"} :tabIndex "0"}]]]
        [:div.text-center.dropdown-footer
         [:a {:href "text-center"} "VIEW ALL"]]]]
      [:div.dropdown.nav-item.main-header-notification
       [:a.new.nav-link {:href "#"} [:i.ti-bell] [:span.pulse]]
       [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
        [:div.menu-header-content.text-left.d-flex
         [:div
          [:h6.menu-header-title.text-white.mb-0
           "7 new Notifications"]]
         [:div.my-auto.ml-auto
          [:span.badge.badge-pill.badge-warning.float-right
           "Mark All Read"]]]
        [:div.main-notification-list.notify-scroll.ps
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-success-transparent
          [:i.la.la-shopping-basket.text-success]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "New Order Received"]
          [:div.notification-subtext "1 hour ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-danger-transparent
          [:i.la.la-user-check.text-danger]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "22 verified registrations"]
          [:div.notification-subtext "2 hour ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-primary-transparent
          [:i.la.la-check-circle.text-primary]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "Project has been approved"]
          [:div.notification-subtext "4 hour ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-pink-transparent
          [:i.la.la-file-alt.text-pink]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "New files available"]
          [:div.notification-subtext "10 hour ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-warning-transparent
          [:i.la.la-envelope-open.text-warning]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "New review received"]
          [:div.notification-subtext "1 day ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:a.d-flex.p-3.border-bottom {:href "#"}]
         [:div.notifyimg.bg-purple-transparent
          [:i.la.la-gem.text-purple]]
         [:div.ml-3
          [:h5.notification-label.mb-1 "Updates Available"]
          [:div.notification-subtext "2 days ago"]]
         [:div.ml-auto [:i.las.la-angle-right.text-right.text-muted]]
         [:div.ps__rail-x
          {:style {:left "0px" :top "0px"}}
          [:div.ps__thumb-x
           {:style {:left "0px" :width "0px"} :tabIndex "0"}]]
         [:div.ps__rail-y
          {:style {:top "0px" :right "0px"}}
          [:div.ps__thumb-y
           {:style {:top "0px" :height "0px"} :tabIndex "0"}]]]
        [:div.dropdown-footer [:a {:href ""} "VIEW ALL"]]]]
      [:div.dropdown.main-profile-menu.nav.nav-item.nav-link
       [:a.profile-user
        {:href ""}
        [:img {:src "/assets/img/faces/5.jpg" :alt ""}]]
       [:div.dropdown-menu.dropdown-menu-arrow.animated.fadeInUp
        [:div.main-header-profile.header-img
         [:div.main-img-user
          [:img {:src "/assets/img/faces/5.jpg" :alt ""}]]
         [:h6 "Elizabeth Jane"]
         [:span "Premium Member"]]
        [:a.dropdown-item {:href ""} [:i.far.fa-user] " My Profile"]
        [:a.dropdown-item {:href ""} [:i.far.fa-edit] " Edit Profile"]
        [:a.dropdown-item
         {:href ""}
         [:i.far.fa-clock]
         " Activity Logs"]
        [:a.dropdown-item
         {:href ""}
         [:i.fas.fa-sliders-h]
         " Account Settings"]
        [:a.dropdown-item
         {:href "page-signin.html"}
         [:i.fas.fa-sign-out-alt]
         " Sign Out"]]]
      [:div.dropdown.main-header-message.right-toggle
       [:a.nav-link
        {:data-target ".sidebar-right" :data-toggle "sidebar-right"}
        [:i.ti-menu.tx-20.bg-transparent]]]]]]])




