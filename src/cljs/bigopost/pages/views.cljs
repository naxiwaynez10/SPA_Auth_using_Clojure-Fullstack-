(ns bigopost.pages.views
  (:require [bigopost.components.breadcrumb :as breadcrumb]
            [bigopost.components.left-side-menu :as left-menu]
            [bigopost.components.loader :refer [loader]]
            [bigopost.components.top-menu :refer [main-header-center
                                                  main-header-left main-header-right
                                                  responsive-header]]
            [bigopost.pages.aboutpage :refer [about-page]]
            [bigopost.pages.authpage :as authpage]
            [bigopost.pages.homepage :refer [home-page]]
            [fipp.edn :as fedn]))

(defn page [state view]
  (if (:loading? state)
    [loader]
    [:<>
     [left-menu/main]
     [:div.main-content.app-content
      [:div.main-header.sticky.side-header.nav.nav-item.sticky-pin
       [:div.container-fluid
        [main-header-left]
        [main-header-center]
        [main-header-right]]]
      [responsive-header]
      [:div.container-fluid
       [breadcrumb/main]
       [:div.row
        [view]]]]]))

(defn main-panel [state]
  (let [active-route (-> state :match :data :name)]
    (case active-route
      (or :dashboard-one :dashboard-two) [page state home-page]
      :about  [page state about-page]
      :login [authpage/login-page]
      :register [authpage/register-page]
      [:pre (with-out-str (fedn/pprint active-route))])))

