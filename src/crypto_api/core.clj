(ns crypto-api.core
  (:require [crypto-api.utils :as utils]))

(defn -main [& args]
  (println (utils/get-api-key :btce)))
