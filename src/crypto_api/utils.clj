(ns crypto-api.utils
  [:use [pandect.core]]
  [:require [org.httpkit.client :as http]
   [clojure.data.json :as json]
   [clojure.java.io :as io]])

; Constants
(def api-urls {:btce "https://btc-e.com/tapi" :bitfinex "https://api.bitfinex.com/v1"})
(def user-agent "crypto-api 0.1.4")
(def config (with-open [r (io/reader "resources/api_keys.cfg")]
              (read (java.io.PushbackReader. r))))

(defn load-config [filename]
  (with-open [r (io/reader filename)]
    (read (java.io.PushbackReader. r))))

(defn get-api-key
  "Loads the API keys for the required exchange."
  [api]
  (config api))

; Helper functions
(defn success? [response]
  (= 200 (:status response)))

(defn nonce
  []
  (int (/ (System/currentTimeMillis) 1000)))

(defn sign-params [credentials params]
  (sha512-hmac
   (clojure.string/join
    "&"
    (for [[k v] params]
      (format "%s=%s" k v))) (:private credentials)))

(defn get-request
  [method & [params]]
  (let [options {:method :get
                 :user-agent user-agent
                 :insecure? false
                 :content-type "application/json"
                 :form-params params}]
    (let [response @(http/get method options)]
      (if (success? response) (json/read-str (response :body))))))

(defn post-request
  [api params]
  (let [credentials (get-api-key api)]
    (let [options {:method :post
                   :user-agent user-agent
                   :insecure? false
                   :content-type "application/json"
                   :headers {"Key" (:public credentials) "Sign" (sign-params credentials params) }
                   :form-params params}]
      (let [response @(http/post (api api-urls) options)]
        (if (success? response)
          (json/read-str (response :body) :key-fn keyword))))))
