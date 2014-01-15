(ns jvm-demo.gen
  [:use [clojure.string :only [join]]]
  (:gen-class))

(defn- calc-stack-size [body]
  10)

(defn- join-lines [& lines]
  (join "\n" lines))

(defn gen-class-initializer [super]
  (join-lines
   ".method public <init>()V"
   "aload_0"
   (str "invokeonvirtual " super "/<init>()V")
   "return"
   ".end method"))

(defn gen-class-header [name super]
  (join-lines
   (join " " [".class public" name])
   (join " " [".super" super])))

(defn gen-class [& {:keys [name super contents]
                    :or {super "java/lang/Object"
                         contents nil}}]
  (join-lines
   (gen-class-header name super)
   (gen-class-initializer super)
   contents))

(defn gen-main [body]
  (join-lines
   ".method public static Main([Ljava/lang/String;)V"
   (join " " [".limit stack" (calc-stack-size body)])
   body
   "return"
   ".end method"))
