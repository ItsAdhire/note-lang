(ns note-lang.wave.adsr
  (:require [note-lang.wave.core :as wave]
            [note-lang.notes :as notes]))

(defn- residual [note len release-amp]
  (take len (map * 
                 (wave/wave (notes/note->freq note) 20 8800 10)
                 (concat release-amp (repeat 0)))))

(defn- create-residuals [notes lens amps]
  (concat 
    (repeat (first lens) 0)
    (mapcat residual notes (concat (rest lens) '(100)) amps)))

(defn- list-of-rel-amps [nested-rels]
  (map :r (flatten (wave/nested-map #(select-keys % [:r]) 
                                    nested-rels))))

(defn- release-amp [last-amp release]
 (range last-amp 0 (/ last-amp release -1)))

(defn- adsr-amps [peak attack decay release len]
 (let [attack-amp (range 0 peak (/ peak attack))
       decay-amp  (range peak 1 (/ (dec peak) decay -1))
       ads-amp    (concat attack-amp decay-amp (repeat 1))
       ads-vec    (vec (take len ads-amp))]
  {:ads ads-vec 
   :r (release-amp (peek ads-vec) release)}))

(defn- apply-adsr-unit [unit-wave peak attack decay release]
  (update (adsr-amps peak attack decay release (count unit-wave))
          :ads #(map * unit-wave %)))

(defn adsr-linear [song wave peak attack decay release]
  (let [ads-r-wave 
        (wave/nested-map 
          #(apply-adsr-unit % peak attack decay release) 
          wave)
        lens     (flatten (wave/nested-map count wave))
        rel-amps (list-of-rel-amps ads-r-wave)]
    [(wave/nested-map :ads ads-r-wave)
     (create-residuals (flatten song) lens rel-amps)]))
