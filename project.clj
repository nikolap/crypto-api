(defproject crypto-api "0.1.4-SNAPSHOT"
  :description "A library of different crypto currency exchange APIs for Clojure"
  :url "https://github.com/nikolap/crypto-api"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.json "0.2.4"]
                 [http-kit "2.1.16"]
                 [pandect "0.3.0"]]
  :main crypto-api.core)
