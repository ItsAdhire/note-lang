(ns note-lang.wave.ads)

(defn- attack-amp [peak len]
  (range 0 peak (/ peak len)))

(defn- decay-amp [peak len]
  (range peak 1 (/ peak len -1)))

(defn- ads-amp [amp len]
  (concat
    (attack-amp amp len)
    (decay-amp amp len)
    (repeat 1)))
  
(defn- ads-pulse [wave attack]
  (let [attack-len (/ (count wave) 4)]
    (map * wave (ads-amp attack attack-len))))

(defn- nested? [wave]
  (sequential? (first wave)))

(defn ads-linear [nested-wave attack]
  (if (nested? nested-wave)
    (map #(ads-linear % attack) nested-wave)
    (ads-pulse nested-wave attack)))
