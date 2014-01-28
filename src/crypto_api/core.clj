(ns crypto-api.core
  (:require [crypto-api.btce :as btce]))


(def api (btce/credentials "PUBLIC" "PRIVATE"))

(defn -main [& args]
  (println (btce/trans-history api)))
