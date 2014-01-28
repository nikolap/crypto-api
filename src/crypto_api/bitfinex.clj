(ns crypto-api.bitfinex
  "Library for the Bitfinex API.
  See https://www.bitfinex.com/pages/api"
  (:require [crypto-api.utils :as utils]))

; Constants
(def api-url "https://api.bitfinex.com/v1")

; API calls
; Public calls

;GET /ticker/:symbol
;GET /today/:symbol
;GET /lendbook/:currency
;GET /book/:symbol
;GET /trades/:symbol
;GET /lends/:currency
;GET /symbols

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
