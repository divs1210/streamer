```
Study hard what interests you the most in the most undisciplined, irreverent and original manner possible. 

- Richard Feynmann
```

# streamer

## What

threading macro(s) for transducers / transducers reimagined as streams

## Usage

```clojure
(require '[streamer.core :refer [=> transduce! seq! into!]])

;; Ex. 1
;; =====
;; this code
(=> (range 10)
    (take 5)
    (map inc)
    (transduce! *))

;; is the same as
(->> (range 10)
     (take 5)
     (map inc)
     (reduce *))

;; and compiles to
((fn [%xform %coll]
   (transduce %xform * %coll)) (comp (take 5) (map inc))
                               (range 10))

;; and could also be written as
(=> (range 5)
    (map inc)
    (transduce %xform * %coll))


;; Ex. 2
;; =====
;; this code
(=> (range 10)
    (filter odd?)
    (map inc)
    (seq!))

;; is the same as
(->> (range 10)
     (filter odd?)
     (map inc))

;; and compiles to
((fn [%xform %coll]
   (sequence %xform %coll)) (comp (filter odd?) (map inc))
                            (range 10))

;; and could also be written as
(=> (range 10)
    (filter odd?)
    (map inc)
    (sequence %xform %coll))


;; Ex. 3
;; =====
;; this code
(require '[net.cgrand.xforms :as x])
(=> (range 8)
    (x/partition 2)
    (map vec)
    (into! {}))

;; is the same as
(->> (range 8)
     (partition 2)
     (map vec)
     (into {}))

;; and compiles to
((fn [%xform %coll]
   (into {} %xform %coll)) (comp (x/partition 2) (map vec))
                           (range 8))

;; and could also be written as
(=> (range 8)
    (x/partition 2)
    (map vec)
    (into {} %xform %coll))
```

## Profit?

```clojure
(time (dotimes [_ 1000000]
        (=> (range 10)
            (filter even?)
            (map #(Math/sqrt %))
            (transduce! *))))
"Elapsed time: 550.802941 msecs"

(time (dotimes [_ 1000000]
        (->> (range 10)
             (filter even?)
             (map #(Math/sqrt %))
             (reduce *))))
"Elapsed time: 1157.937817 msecs"
```

## License

Copyright © 2018 Divyansh Prakash

Distributed under the Eclipse Public License either version 1.0.
