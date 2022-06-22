(ns bigopost.components.left-side-menu
  (:require 
  [reitit.frontend.easy :as rfe] 
   ))

"For side menu items"

(defn main-sidebar-header []
  [:div.main-sidebar-header
   [:a.desktop-logo.logo-light
    {:href "/"}
    [:img.main-logo
     {:alt "logo" :src "/assets/img/brand/logo.png"}]]
   [:a.desktop-logo.logo-dark
    {:href "/"}
    [:img.main-logo.dark-theme
     {:alt "logo" :src "/assets/img/brand/logo-white.png"}]]
   [:a.logo-icon.mobile-logo.icon-light
    {:href "/"}
    [:img.logo-icon
     {:alt "logo" :src "/assets/img/brand/favicon.png"}]]
   [:a.logo-icon.mobile-logo.icon-dark
    {:href "/"}
    [:img.logo-icon.dark-theme
     {:alt "logo"
      :src "/assets/img/brand/favicon-white.png"}]]])

(defn main-sidebar-body []
  [:div.main-sidebar-body.circle-animation
   [:ul.side-menu.circle.open
    [:li [:h3 "Dashboard"]]
    [:li.slide
     [:a.side-menu__item
      {:href (rfe/href :dashboard-one)}
      [:i.side-menu__icon.ti-desktop]
      [:span.side-menu__label "Dashboard"]]]
    [:li [:h3 "Posts"]]
    ;; [:li.slide
    ;;  [:a.side-menu__item
    ;;   {:href "/"}
    ;;   [:i.side-menu__icon.ti-desktop]
    ;;   [:span.side-menu__label "Home"]]]
    [:li.slide
     [:a.side-menu__item
      {:href (rfe/href :about)}
      [:i.side-menu__icon.ti-desktop]
      [:span.side-menu__label "About"]]]]])

(defn main []
  [:<>
   [:div.app-sidebar__overlay {:data-toggle "sidebar"}]
   [:aside.app-sidebar.sidebar-scroll.ps
    [main-sidebar-header]
    [main-sidebar-body]
    [:div.ps__rail-x
     {:style {:left "0px" :top "0px"}}
     [:div.ps__thumb-x {:style {:left "0px" :width "0px"} :tabIndex "0"}]]
    [:div.ps__rail-y
     {:style {:top "0px" :height "458px" :right "0px"}}
     [:div.ps__thumb-y
      {:style {:top "0px" :height "0px"} :tabIndex "0"}]]]])