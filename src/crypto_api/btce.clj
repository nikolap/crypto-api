(ns crypto-api.btce
  "Library for the BTC-E API.
  See https://btc-e.com/api/documentation"
  (:use [pandect.core])
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]
            [crypto-api.utils :as utils]))

; Constants
(def api-url "https://btc-e.com/tapi")
(def public-url "https://btc-e.com/api/3/")
(def credentials (utils/get-api-key :btce))

; Helper functions
(defn success? [response]
  (= 200 (:status response)))

(defn nonce
  []
  (int (/ (System/currentTimeMillis) 1000)))

(defn sign-params [params]
  (sha512-hmac
   (clojure.string/join
    "&"
    (for [[k v] params]
      (format "%s=%s" k v))) (:private credentials)))

(defn get-request
  [method]
  (let [options {:method :get
                 :user-agent "crypto-api 0.1.0"
                 :insecure? false
                 :content-type "application/json"}]
    (let [response @(http/get method options)]
      (if (success? response) (json/read-str (response :body))))))

(defn post-request
  [params]
  (let [options {:method :post
                 :user-agent "crypto-api 0.1.0"
                 :insecure? false
                 :content-type "application/json"
                 :headers {"Key" (:public cred) "Sign" (sign-params params) }
                 :form-params params}]
    (let [response @(http/post api-url options)]
      (if (success? response)
        (json/read-str (response :body) :key-fn keyword)))))

; API calls
; Public calls
(defn get-public-call
  [url]
  (let [method url]
    (get-request method)))

(defn format-url
  [currency-pair method]
  (format "%s%s/%s" public-url (name method) (name currency-pair)))

(defn get-trade-fee
  [c1 c2]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "fee")]
    (get-public-call base-url)))

(defn get-ticker
  [c1 c2]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "ticker")]
    (get-public-call base-url)))

(defn get-depth
  [c1 c2]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "depth")]
    (get-public-call base-url)))

(defn get-trades-history
  [c1 c2]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "trades")]
    (get-public-call base-url)))

; Private calls
(defn get-info
  []
  (post-request {"method" "getInfo" "nonce" (nonce)}))

; TODO optional params
(defn trans-history
  []
  (post-request {"method" "TransHistory" "nonce" (nonce)}))

; TODO optional params
(defn trade-history
  []
  (post-request {"method" "TradeHistory" "nonce" (nonce)}))

; TODO optional params
(defn active-orders
  []
  (post-request {"method" "ActiveOrders" "nonce" (nonce)}))

(defn trade
  [c1 c2 c-type rate amount]
  (post-request {"method" "ActiveOrders" "nonce" (nonce) "pair"
                      (format "%s_%s" (name c1) (name c2)) "type" c-type "rate" rate "amount" amount}))

(defn cancel-order
  [order-id]
  (post-request {"method" "ActiveOrders" "nonce" (nonce) "order_id" order-id}))
