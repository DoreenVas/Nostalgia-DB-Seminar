#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import os
import glob
import hdf5_getters
import re

class Song:
    songCount = 0
    songDictionary = {}

    def __init__(self, songID):
        self.id = songID
        Song.songCount += 1
        Song.songDictionary[songID] = self

        self.albumName = None
        self.albumID = None
        self.artistID = None
        self.artistLatitude = None
        self.artistLocation = None
        self.artistLongitude = None
        self.artistName = None
        self.danceability = None
        self.duration = None
        self.genreList = []
        self.keySignature = None
        self.keySignatureConfidence = None
        self.lyrics = None
        self.popularity = None
        self.tempo = None
        self.timeSignature = None
        self.timeSignatureConfidence = None
        self.title = None
        self.year = None
        self.artistHotttnesss = None
        self.songHotttnesss = None
        self.songEnergy = None
        self.songLoudness = None
        self.artistFmiliarity = None
        self.similarArtists = None
        self.audio = None

    def displaySongCount(self):
        print "Total Song Count %i" % Song.songCount

    def displaySong(self):
        print "ID: %s" % self.id

    def setGenreList(self, h5File):
        genre = hdf5_getters.get_artist_terms(h5File)
        self.genreList = genre


def main():
    outputFile1 = open('SongCSV.csv', 'w')
    csvRowString = ""

    #################################################
    #if you want to prompt the user for the order of attributes in the csv,
    #leave the prompt boolean set to True
    #else, set 'prompt' to False and set the order of attributes in the 'else'
    #clause
    prompt = False
    #################################################
    if prompt == True:
        while prompt:

            prompt = False

            csvAttributeString = raw_input("\n\nIn what order would you like the colums of the CSV file?\n" +
                "Please delineate with commas. The options are: " +
                "AlbumName, AlbumID, ArtistID, ArtistLatitude, ArtistLocation, ArtistLongitude,"+
                " ArtistName, Danceability, Duration, KeySignature, KeySignatureConfidence, Tempo," +
                " SongID, TimeSignature, TimeSignatureConfidence, Title, and Year.\n\n" +
                "For example, you may write \"Title, Tempo, Duration\"...\n\n" +
                "...or exit by typing 'exit'.\n\n")

            csvAttributeList = re.split('\W+', csvAttributeString)
            for i, v in enumerate(csvAttributeList):
                csvAttributeList[i] = csvAttributeList[i].lower()

            for attribute in csvAttributeList:
                # print "Here is the attribute: " + attribute + " \n"


                if attribute == 'AlbumID'.lower():
                    csvRowString += 'AlbumID'
                elif attribute == 'AlbumName'.lower():
                    csvRowString += 'AlbumName'
                elif attribute == 'ArtistID'.lower():
                    csvRowString += 'ArtistID'
                elif attribute == 'ArtistLatitude'.lower():
                    csvRowString += 'ArtistLatitude'
                elif attribute == 'ArtistLocation'.lower():
                    csvRowString += 'ArtistLocation'
                elif attribute == 'ArtistLongitude'.lower():
                    csvRowString += 'ArtistLongitude'
                elif attribute == 'ArtistName'.lower():
                    csvRowString += 'ArtistName'
                elif attribute == 'Danceability'.lower():
                    csvRowString += 'Danceability'
                elif attribute == 'Duration'.lower():
                    csvRowString += 'Duration'
                elif attribute == 'ArtistHotttnesss'.lower():
                    csvRowString += 'ArtistHotttnesss'
                elif attribute == 'KeySignature'.lower():
                    csvRowString += 'KeySignature'
                elif attribute == 'KeySignatureConfidence'.lower():
                    csvRowString += 'KeySignatureConfidence'
                elif attribute == 'SongID'.lower():
                    csvRowString += "SongID"
                elif attribute == 'SongHotttnesss'.lower():
                    csvRowString += 'SongHotttnesss'
                elif attribute == 'Tempo'.lower():
                    csvRowString += 'Tempo'
                elif attribute == 'Loudness'.lower():
                    csvRowString += 'Loudness'
                elif attribute == 'Energy'.lower():
                    csvRowString += 'Energy'
                elif attribute == 'TimeSignature'.lower():
                    csvRowString += 'TimeSignature'
                elif attribute == 'TimeSignatureConfidence'.lower():
                    csvRowString += 'TimeSignatureConfidence'
                elif attribute == 'Title'.lower():
                    csvRowString += 'Title'
                elif attribute == 'Year'.lower():
                    csvRowString += 'Year'
                elif attribute == 'Exit'.lower():
                    sys.exit()
                else:
                    prompt = True
                    print "=============="
                    print "I believe there has been an error with the input."
                    print "=============="
                    break

                csvRowString += ","

            lastIndex = len(csvRowString)
            csvRowString = csvRowString[0:lastIndex-1]
            csvRowString += "\n"
            outputFile1.write(csvRowString);
            csvRowString = ""
    #else, if you want to hard code the order of the csv file and not prompt
    #the user, 
    else:
        #################################################
        #change the order of the csv file here
        #Default is to list all available attributes (in alphabetical order)
        csvRowString = ("SongID,AlbumID,AlbumName,ArtistID,ArtistHotttnesss,ArtistFamiliarity,ArtistLatitude,ArtistLocation,"+
            "ArtistLongitude,ArtistName,Danceability,Duration,KeySignature,"+
            "KeySignatureConfidence,Tempo,TimeSignature,TimeSignatureConfidence,"+
            "Title,SongHotttnesss,Loudness,Energy,Year,SimilarArtists,Genre,Audio")
        #################################################

        csvAttributeList = re.split('\W+', csvRowString)
        for i, v in enumerate(csvAttributeList):
            csvAttributeList[i] = csvAttributeList[i].lower()
        outputFile1.write("SongNumber,");
        outputFile1.write(csvRowString + "\n");
        csvRowString = ""  

    #################################################

    #TODO Enter base folder here
    #Set the basedir here, the root directory from which the search
    #for files stored in a (hierarchical data structure) will originate
    basedir = "C:\Users\NadavSpitzer\Documents\מדמח\שנה ג'\סמסטר א'\סדנה במסדי נתונים\DB\MillionSongSubset\data" # "." As the default means the current directory
    ext = ".h5" #Set the extension here. H5 is the extension for HDF5 files.
    #################################################

    #FOR LOOP
    for root, dirs, files in os.walk(basedir):        
        files = glob.glob(os.path.join(root,'*'+ext))
        for f in files:
            print f

            songH5File = hdf5_getters.open_h5_file_read(f)
            song = Song(str(hdf5_getters.get_song_id(songH5File)))

            testDanceability = hdf5_getters.get_danceability(songH5File)
            #print type(testDanceability)
            #print ("Here is the danceability: ") + str(testDanceability)

            song.artistID = str(hdf5_getters.get_artist_id(songH5File))
            song.albumID = str(hdf5_getters.get_release_7digitalid(songH5File))
            song.albumName = str(hdf5_getters.get_release(songH5File))
            song.artistLatitude = str(hdf5_getters.get_artist_latitude(songH5File))
            song.artistLocation = str(hdf5_getters.get_artist_location(songH5File))
            song.artistLongitude = str(hdf5_getters.get_artist_longitude(songH5File))
            song.artistName = str(hdf5_getters.get_artist_name(songH5File))
            song.danceability = str(hdf5_getters.get_danceability(songH5File))
            song.duration = str(hdf5_getters.get_duration(songH5File))
            song.artistHotttnesss = str(hdf5_getters.get_artist_hotttnesss(songH5File))
            song.keySignature = str(hdf5_getters.get_key(songH5File))
            song.keySignatureConfidence = str(hdf5_getters.get_key_confidence(songH5File))
            song.lyrics = None
            song.popularity = None
            song.tempo = str(hdf5_getters.get_tempo(songH5File))
            song.timeSignature = str(hdf5_getters.get_time_signature(songH5File))
            song.timeSignatureConfidence = str(hdf5_getters.get_time_signature_confidence(songH5File))
            song.songHotttnesss = str(hdf5_getters.get_song_hotttnesss(songH5File))
            song.title = str(hdf5_getters.get_title(songH5File))
            song.year = str(hdf5_getters.get_year(songH5File))
            song.setGenreList(songH5File)
            song.songLoudness = str(hdf5_getters.get_loudness(songH5File))
            song.songEnergy = str(hdf5_getters.get_energy(songH5File))
            song.artistFmiliarity = str(hdf5_getters.get_artist_familiarity(songH5File))
            song.similarArtists = hdf5_getters.get_similar_artists(songH5File)
            song.audio = hdf5_getters.get_audio_md5(songH5File)

            #print song count
            csvRowString += str(song.songCount) + ","

            for attribute in csvAttributeList:
                # print "Here is the attribute: " + attribute + " \n"

                if attribute == 'AlbumID'.lower():
                    csvRowString += song.albumID
                elif attribute == 'AlbumName'.lower():
                    albumName = song.albumName
                    albumName = albumName.replace(',',"")
                    csvRowString += "\"" + albumName + "\""
                elif attribute == 'ArtistID'.lower():
                    csvRowString += "\"" + song.artistID + "\""
                elif attribute == 'ArtistLatitude'.lower():
                    latitude = song.artistLatitude
                    if latitude == 'nan':
                        latitude = ''
                    csvRowString += latitude
                elif attribute == 'ArtistLocation'.lower():
                    location = song.artistLocation
                    location = location.replace(',','')
                    csvRowString += "\"" + location + "\""
                elif attribute == 'ArtistLongitude'.lower():
                    longitude = song.artistLongitude
                    if longitude == 'nan':
                        longitude = ''
                    csvRowString += longitude                
                elif attribute == 'ArtistName'.lower():
                    csvRowString += "\"" + song.artistName + "\""                
                elif attribute == 'Danceability'.lower():
                    csvRowString += song.danceability
                elif attribute == 'Duration'.lower():
                    csvRowString += song.duration
                elif attribute == 'ArtistHotttnesss'.lower():
                    csvRowString += song.artistHotttnesss
                elif attribute == 'ArtistFamiliarity'.lower():
                    csvRowString += song.artistFmiliarity
                elif attribute == 'KeySignature'.lower():
                    csvRowString += song.keySignature
                elif attribute == 'KeySignatureConfidence'.lower():
                    # print "key sig conf: " + song.timeSignatureConfidence                                 
                    csvRowString += song.keySignatureConfidence
                elif attribute == 'SongID'.lower():
                    csvRowString += "\"" + song.id + "\""
                elif attribute == 'SongHotttnesss'.lower():
                    csvRowString += song.songHotttnesss
                elif attribute == 'Tempo'.lower():
                    # print "Tempo: " + song.tempo
                    csvRowString += song.tempo
                elif attribute == 'Loudness'.lower():
                    csvRowString += song.songLoudness
                elif attribute == 'Energy'.lower():
                    csvRowString += song.songEnergy
                elif attribute == 'TimeSignature'.lower():
                    csvRowString += song.timeSignature
                elif attribute == 'TimeSignatureConfidence'.lower():
                    # print "time sig conf: " + song.timeSignatureConfidence                                   
                    csvRowString += song.timeSignatureConfidence
                elif attribute == 'Title'.lower():
                    csvRowString += "\"" + song.title + "\""
                elif attribute == 'Year'.lower():
                    csvRowString += song.year
                elif attribute == 'SimilarArtists'.lower():
                    csvRowString += "\""
                    for s in song.similarArtists:
                        csvRowString += "\"\"" + str(s) + "\"\"" + ","
                    csvRowString = csvRowString.rstrip(",")
                    csvRowString += "\""
                elif attribute == 'Genre'.lower():
                    csvRowString += "\""
                    for g in song.genreList:
                        csvRowString += "\"\"" + str(g) + "\"\"" + ","
                    csvRowString = csvRowString.rstrip(",")
                    csvRowString += "\""
                elif attribute == 'Audio'.lower():
                    csvRowString += song.audio
                else:
                    csvRowString += "Erm. This didn't work. Error. :( :(\n"

                csvRowString += ","

            #Remove the final comma from each row in the csv
            lastIndex = len(csvRowString)
            csvRowString = csvRowString[0:lastIndex-1]
            csvRowString += "\n"
            outputFile1.write(csvRowString)
            csvRowString = ""

            songH5File.close()

    outputFile1.close()

main()