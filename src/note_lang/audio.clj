(ns note-lang.audio
  (:require [clojure.java.io :as io]))

(import javax.sound.sampled.AudioInputStream
        javax.sound.sampled.AudioFormat
        javax.sound.sampled.AudioFileFormat$Type
        javax.sound.sampled.AudioSystem)

(defn- sin [x] 
 (Math/sin x))

(defn wave [freq vol bitrate duration]
  (let [domain  (* freq 2 Math/PI)
        amp     (partial * vol)
        step    (/ domain bitrate)
        samples (* bitrate duration)]
    (->> (range)
         (map (comp amp sin (partial * step)))
         (take samples))))

;-------

(def note->freq
  {:e4 329.63
   :a4 440
   :c5 523.25
   :e5 659.25
   :g5 783.99
   :b5 987.77
   :rest 0})


(defn song->wave [song vol bitrate duration]
 (->> song
      (map note->freq)
      (mapcat #(wave % vol bitrate duration))))

;-------

(defn- create-format [bitrate]
  (new AudioFormat bitrate 8 1 true true))

(defn- create-audio-istream [istream bitrate length]
  (new AudioInputStream 
       istream 
       (create-format bitrate)
       length))

(defn- byte-clamp [n]
  (-> n
      (double)
      (Math/round)
      (max -128)
      (min 127)))

(defn- wave->audio-istream [wave bitrate]
  (let [len (count wave)]
    (-> (map byte-clamp wave)
        (byte-array)
        (io/input-stream)
        (create-audio-istream bitrate len))))

(defn save-wave [wave bitrate file-path]
  (AudioSystem/write
    (wave->audio-istream wave bitrate)
    AudioFileFormat$Type/WAVE
    (io/file file-path)))

(defn play-wave [wave bitrate]
  (doto (AudioSystem/getClip)
    (.open (wave->audio-istream wave bitrate))
    (.start)))

;-------

(defn- apply-song-as-wave [song vol bitrate duration f]
      (f (song->wave song vol bitrate duration)))

(defn save [song vol bitrate duration file-path]
  (apply-song-as-wave song
                      vol 
                      bitrate
                      duration 
                      #(save-wave % bitrate file-path)))

(defn play [song vol bitrate duration]
  (apply-song-as-wave song
                      vol 
                      bitrate
                      duration 
                      #(play-wave % bitrate)))
