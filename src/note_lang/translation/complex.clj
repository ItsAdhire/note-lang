(ns note-lang.translation.complex)

(defn cos [x] (Math/cos x))
(defn sin [x] (Math/sin x))
(defn pow [base x] (Math/pow base x))

(defn complex [real imag]
  {:real real
   :imag imag})

(defn add [c1 c2]
  (complex
    (+ (:real c1) (:real c2))
    (+ (:imag c1) (:imag c2))))

(defn scale [c r]
  (update-vals c (partial * r)))
  
(defn hypot [c]
  (Math/hypot (:real c) (:imag c)))

(defn e-pow [c]
  (let [r (:real c)
        i (:imag c)]
    (scale (complex (cos i) (sin i)) 
           (pow Math/E r))))
