(ns jvm-demo.core
  [:use [jvm-demo.gen]]
  (:gen-class))

(declare translate)

(defn binary-op [op args]
  (join-lines (translate (first args)) (translate (second args)) op))

(def builtins
  {:add #(binary-op "iadd" %)
   :sub #(binary-op "isub" %)
   :mul #(binary-op "imul" %)
   :div #(binary-op "idiv" %)
   :println #(join-lines "getstatic java/lang/System/out Ljava/io/PrintStream;"
                         (translate (first %1))
                         "invokevirtual java/io/PrintStream/println(I)V")})

(defn translate [prog]
  (if (vector? prog)
    (let [sym (first prog)
          args (rest prog)]
      (cond
       (contains? builtins sym) ((builtins sym) args)
       :else (throw (Exception. (format "Unknown symbol: %s" (str sym))))))
    (cond
     (number? prog) (format "ldc %d" prog)
     :else (throw (Exception. (format "Unhandled literal: %s" (str prog)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println
   (gen-cls
    :name "Main"
    :contents (gen-main (translate [:println [:add 2 3]])))))
