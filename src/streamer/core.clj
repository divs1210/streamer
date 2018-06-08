(ns streamer.core)

(defmacro seq!
  []
  `(sequence ~'%xform ~'%coll))

(defmacro into!
  [to]
  `(into ~to ~'%xform ~'%coll))

(defmacro reduce!
  ([f]
   `(transduce ~'%xform ~f ~'%coll))
  ([f init]
   `(transduce ~'%xform ~f ~init ~'%coll)))

(defmacro =>
  [coll & xforms]
  (let [[xforms transducer-fn] ((juxt butlast last) xforms)]
    `((fn [~'%xform ~'%coll]
        ~transducer-fn) ~(cons `comp xforms) ~coll)))
