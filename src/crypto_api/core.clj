(ns crypto-api.core
  (:require [crypto-api.btce :as btce]
            [crypto-api.bitfinex :as bitfinex]
            [crypto-api.utils :as utils]))

(defn -main [& args]
  (println (btce/get-info)))
