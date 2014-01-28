(ns crypto-api.utils)

; Constants
(def config (slurp "resources/test_api_keys.cfg"))

(defn get-api-key
  "Loads the API keys for the required exchange."
  [api]
  (test-var api))
