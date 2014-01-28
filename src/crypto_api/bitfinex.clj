(ns crypto-api.bitfinex
  "Library for the Bitfinex API.
  See https://www.bitfinex.com/pages/api"
  (:require [crypto-api.utils :as utils]))

; Constants
(def api-url "https://api.bitfinex.com/v1")

; Helper functions
(defn format-url
  [method arg]
  (format "%s/%s/%s" api-url (name method) (name arg)))

; API calls
; Public calls
(defn get-ticker
  [c-symbol]
  (utils/get-request (format-url "ticker" c-symbol)))

(defn get-today
  [c-symbol & {:keys [limit-bids limit-asks]}]
  (let [params {(if (nil? limit-bids) "" ("limit_bids" limit-bids)) (if (nil? limit-asks) "" ("limit_asks" limit-asks))}]
    (utils/get-request (format-url "today" c-symbol) params)))

(defn get-lendbook
  [currency & {:keys [limit-bids limit-asks]}]
  (let [params {(if (nil? limit-bids) "" ("limit_bids" limit-bids)) (if (nil? limit-asks) "" ("limit_asks" limit-asks))}]
    (utils/get-request (format-url "lendbook" currency) params)))

(defn get-book
  [c-symbol & {:keys [limit-bids limit-asks]}]
  (let [params {(if (nil? limit-bids) "" ("limit_bids" limit-bids)) (if (nil? limit-asks) "" ("limit_asks" limit-asks))}]
    (utils/get-request (format-url "book" c-symbol) params)))

(defn get-trades
  [c-symbol & {:keys [timestamp limit-trades]}]
  (let [params {(if (nil? timestamp) "" ("timestamp" timestamp)) (if (nil? limit-trades) "" ("limit_trades" limit-trades))}]
    (utils/get-request (format-url "trades" c-symbol) params)))

(defn get-lends
  [currency & {:keys [timestamp limit-lends]}]
  (let [params {(if (nil? timestamp) "" ("timestamp" timestamp)) (if (nil? limit-lends) "" ("limit_lends" limit-lends))}]
    (utils/get-request (format-url "lends" currency) params)))

(defn get-symbols
  []
  (utils/get-request (format-url "symbols" "")))

; Private calls
;POST /order/new
;POST /order/new/multi
;POST /order/cancel
;POST /order/cancel/multi
;GET /order/cancel/all
;POST /order/cancel/replace
;POST /order/status
;POST /orders
;POST /positions
;POST /mytrades
;POST /offer/new
;POST /offer/cancel
;POST /offer/status
;POST /offers
;POST /credits
;POST /balances
