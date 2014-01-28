(ns crypto-api.btce
  "Library for the BTC-E API.
  See https://btc-e.com/api/documentation"
  (:require [crypto-api.utils :as utils]))

; Constants
(def public-url "https://btc-e.com/api/3/")

; API calls
; Public calls
(defn get-public-call
  [url]
  (let [method url]
    (utils/get-request method)))

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
  [& {:keys [c1 c2]}]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "trades")]
    (get-public-call base-url)))

; Private calls
(defn get-info
  []
  (utils/post-request :btce {"method" "getInfo" "nonce" (utils/nonce)}))

(defn trans-history
  [& {:keys [from t-count from-id end-id order since end]
      :or {from "" t-count "" from-id "" end-id "" order "" since "" end ""}}]
  (utils/post-request :btce {"method" "TransHistory" "nonce" (utils/nonce)}))

(defn trade-history
  [& {:keys [from t-count from-id end-id order since end c1 c2]
      :or {from "" t-count "" from-id "" end-id "" order "" since "" end ""}}]
  (utils/post-request :btce {"method" "TradeHistory" "nonce" (utils/nonce) "from" from "count" t-count
                             "from_id" from-id "end_id" end-id "order" order "since" since "end" end
                             "pair" (if-not (nil? c1)
                                      (format "%s_%s" (name c1) (name c2))
                                      "")}))

(defn active-orders
  [& {:keys [c1 c2]}]
  (utils/post-request :btce {"method" "ActiveOrders" "nonce" (utils/nonce)
                             "pair" (if-not (nil? c1)
                                      (format "%s_%s" (name c1) (name c2))
                                      "")}))

(defn trade
  [c1 c2 c-type rate amount]
  (utils/post-request :btce {"method" "ActiveOrders" "nonce" (utils/nonce) "pair"
                             (format "%s_%s" (name c1) (name c2)) "type" c-type "rate" rate "amount" amount}))

(defn cancel-order
  [order-id]
  (utils/post-request :btce{"method" "ActiveOrders" "nonce" (utils/nonce) "order_id" order-id}))
