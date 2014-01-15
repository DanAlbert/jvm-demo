(defproject jvm-demo "0.1.0-SNAPSHOT"
  :description "Demo compiler for CS 480 Lisp language to JVM."
  :url "http://github.com/DanAlbert/jvm-demo"
  :license {:name "MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot jvm-demo.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
