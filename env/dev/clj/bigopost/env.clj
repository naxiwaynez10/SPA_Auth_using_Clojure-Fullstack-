(ns bigopost.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [bigopost.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[bigopost started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[bigopost has shut down successfully]=-"))
   :middleware wrap-dev})
