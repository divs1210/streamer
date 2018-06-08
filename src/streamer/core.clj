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
(defmacro seq!
  "Creates and returns a seq from the coll and preceding xforms."
  []
  `(sequence ~'%xform ~'%coll))

(defmacro into!
  "Returns a new coll consisting of to-coll with all of the items of
  from-coll conjoined. from-coll is created by applying the preceding
  xforms to the given coll."
  [to]
  `(into ~to ~'%xform ~'%coll))

(defmacro reduce!
  "Dispatches to transduce."
  ([f]
   `(transduce ~'%xform ~f ~'%coll))
  ([f init]
   `(transduce ~'%xform ~f ~init ~'%coll)))
