(ns streamer.core "Use transducers like normal collection functions!")

(defmacro =>
  "Thread coll through xforms, returning value from terminal.
  `xforms` are functions like (map first) and (filter even) that
  return transducers.
  `terminal` is the last form in the `=>` body. It takes %xform and %coll
  as implicit parameters and can be used to generate the final value
  once computation is complete."
  [coll & xforms-and-terminal]
  (let [[xforms terminal] ((juxt butlast last) xforms-and-terminal)]
    `((fn [~'%xform ~'%coll]
        ~terminal) ~(cons `comp xforms) ~coll)))


;; Terminal Forms
;; ==============
(defmacro transduce!
  "Dispatches to `transduce`."
  ([f]
   `(transduce ~'%xform ~f ~'%coll))
  ([f init]
   `(transduce ~'%xform ~f ~init ~'%coll)))

(defmacro sequence!
  "Dispatches to `sequence`."
  []
  `(sequence ~'%xform ~'%coll))

(defmacro into!
  "Dispatches to `into`."
  [to]
  `(into ~to ~'%xform ~'%coll))
