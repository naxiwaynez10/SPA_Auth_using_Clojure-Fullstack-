(ns bigopost.junks
              (:require [reitit.frontend.controllers :as rfc]))

                       ;; Only run the controllers, which are likely to call authenticated APIs,
                       ;; if user has been authenticated.
                       ;; Alternative solution could be to always run controllers,
                       ;; check authentication status in each controller, or check authentication status in API calls.
                      ;; (if-not (:authenticated? state)
                        ;; (assoc state :match (assoc new-match :controllers (rfc/apply-controllers (:controllers (:match state)) new-match)))
                      ;; )