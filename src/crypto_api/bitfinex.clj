(ns crypto-api.bitfinex
  "Library for the Bitfinex API.
  See https://www.bitfinex.com/pages/api"
  (:use [pandect.core])
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))
