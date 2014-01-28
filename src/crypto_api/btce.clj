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
  [c1 c2]
  (let [base-url (format-url (format "%s_%s" (name c1) (name c2)) "trades")]
    (get-public-call base-url)))

; Private calls
(defn get-info
  []
  (utils/post-request :btce {"method" "getInfo" "nonce" (utils/nonce)}))

; TODO optional params
(defn trans-history
  []
  (utils/post-request :btce {"method" "TransHistory" "nonce" (utils/nonce)}))

; TODO optional params
(defn trade-history
  []
  (utils/post-request :btce {"method" "TradeHistory" "nonce" (utils/nonce)}))

; TODO optional params
(defn active-orders
  []
  (utils/post-request :btce {"method" "ActiveOrders" "nonce" (utils/nonce)}))

(defn trade
  [c1 c2 c-type rate amount]
  (utils/post-request :btce {"method" "ActiveOrders" "nonce" (utils/nonce) "pair"
                             (format "%s_%s" (name c1) (name c2)) "type" c-type "rate" rate "amount" amount}))

(defn cancel-order
  [order-id]
  (utils/post-request :btce{"method" "ActiveOrders" "nonce" (utils/nonce) "order_id" order-id}))
