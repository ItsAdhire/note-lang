(ns note-lang.wave.core)

(defn- sin [x] 
 (Math/sin x))

(defn wave [freq vol bitrate duration]
  (let [domain  (* freq 2 Math/PI)
        amp     (partial * vol)
        stepper (partial * domain (/ bitrate))
        samples (* bitrate duration)]
    (->> (range)
         (map (comp amp sin stepper))
         (take samples))))

; TODO make private once adsr-linear is updated to use nested-map
(defn nested? [wave]
 (sequential? (first wave)))

(defn nested-map [f coll]
  (if (nested? coll)
    (map #(nested-map f %) coll)
    (f coll)))
