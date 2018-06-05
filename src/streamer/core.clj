(ns streamer.core
  (:require [clojure.test :as t]))

(defn- switch-marker? [x]
  (or (= x :seq)
      (= x :stream)))

(defn- tag-forms
  [xforms]
  (->> xforms
       (partition-by switch-marker?)
       (map vector (cycle [:stream :skip :seq :skip]))
       (take-nth 2)))

(defmacro =>
  "A threading macro for data processing"
  [stream & forms]
  (loop [remaining-forms (tag-forms forms)
         out-form stream]
    (if (seq remaining-forms)
      (recur (rest remaining-forms)
             (let [[tag form] (first remaining-forms)]
               (case tag
                 :stream
                 (list sequence
                       (cons comp form)
                       out-form)
                 :seq
                 (list (cons comp (reverse form))
                       out-form))))
      out-form)))
