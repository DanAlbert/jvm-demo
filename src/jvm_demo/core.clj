(ns jvm-demo.core
  [:use [jvm-demo.gen]]
  (:gen-class))

;(defn compile [prog]
;  (let [sym (first prog)
;        args (rest prog)]
;    (cond
;     (contains math-ops sym) (gen-math sym args))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (gen-class :name "Main" :contents (gen-main nil))))
