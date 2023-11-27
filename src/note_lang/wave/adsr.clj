(ns note-lang.wave.adsr
  (:require [note-lang.wave.core :as wave]))

(defn- release-amp [last-amp release]
 (range last-amp 0 (/ last-amp release -1)))

(defn- adsr-amps [peak attack decay release len]
 (let [attack-amp (range 0 peak (/ peak attack))
       decay-amp  (range peak 1 (/ (dec peak) decay -1))
       ads-amp    (concat attack-amp decay-amp (repeat 1))
       ads-vec    (vec (take len ads-amp))]
  [ads-vec (release-amp (peek ads-vec) release)]))

(defn adsr-linear [wave peak attack decay release]
 (if (wave/nested? wave)
  (map #(adsr-linear % peak attack decay release) wave)
  (let [[ads-amp rel-amp] 
        (adsr-amps peak attack decay release (count wave))]
   (map * ads-amp wave))))

(defn adsr-linear2 [wave peak attack decay release]
  (wave/nested-map #(adsr-amps peak attack decay release (count %))
                   wave))

(adsr-linear2 [[0 1 2 3 4][1 2 3 4]] 1 1 1 1)
