(defproject divs1210/streamer "1.0.0"
  :description "threading macro(s) for transducers / transducers re-imagined as streams "
  :url "https://github.com/divs1210/streamer"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [net.cgrand/xforms "0.17.0"]]
  :profiles {:dev {:plugins [[lein-cloverage "1.1.1"]]}})
