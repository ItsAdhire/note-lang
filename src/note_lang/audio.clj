(ns note-lang.audio
  (:require [clojure.java.io :as io]
            [note-lang.wave.core :as wave]))

(import javax.sound.sampled.AudioInputStream
        javax.sound.sampled.AudioFormat
        javax.sound.sampled.AudioFileFormat$Type
        javax.sound.sampled.AudioSystem)

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
  (let [flat-wave (flatten wave)
        len       (count flat-wave)]
    (-> (map byte-clamp flat-wave)
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

(defn- apply-song-as-wave [song vol bitrate tempo f]
      (f (wave/song->wave song vol bitrate tempo)))

(defn save [song vol bitrate tempo file-path]
  (apply-song-as-wave song
                      vol 
                      bitrate
                      tempo 
                      #(save-wave % bitrate file-path)))
   
(defn play [song vol bitrate tempo]
  (apply-song-as-wave song
                      vol 
                      bitrate
                      tempo
                      #(play-wave % bitrate)))

