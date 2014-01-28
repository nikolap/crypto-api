# crypto-api

A Clojure API for Crypto-Exchanges. Currently supports BTC-e. Plans for OKCoin, Bitfinex, Bter, and other exchanges. Mostly just me learning Clojure though so very much a work in progress.

## Usage

Add API keys to `resources/test_api_keys.cfg`
Add the library you want to your program, e.g.
```clj
(require '(crypto-api.btce :as btce))
```
Use the calls, e.g.
```clj
(btce/get-info)
```

## License

Copyright © 2014 Nikola Peric

Distributed under the [MIT License](http://www.opensource.org/licenses/MIT).
