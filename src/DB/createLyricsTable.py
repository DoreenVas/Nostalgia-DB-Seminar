#!/usr/bin/env python
# -*- coding: cp65001 -*-
# -*- coding: utf-8 -*-

import csv
import mysql.connector
from mysql.connector import Error
import random

class MySQLConnector:
    def __init__(self):
        """ Connect to MySQL database """
        try:
            # Connecting to the database. Writes a proper message about connection (succeeded/ failed)
            self.conn = mysql.connector.connect(host='localhost',
                                                database='seminarDB',
                                                user='root',
                                                password='supernatrual1234',
                                                charset='utf8mb4',
                                                use_unicode=True)
            if self.conn.is_connected():
                print "Connected to MySQL database"
                self._mycursor = self.conn.cursor()
                self._mycursor.execute('SET NAMES utf8mb4')
                #self._mycursor.execute("SET CHARACTER SET utf8mb4")

        except Error as e:
            print(e)

    def getConnectionStatus(self):
        if self.conn.is_connected:
            return True
        return False

    """ The function executes commit for the DB tables"""

    def commit(self):
        self._mycursor.execute("commit")


def checkItem(wanted_item, from_table, compare_item, compare_value):
    compare_value = compare_value.replace("\"","").replace("\'", "")
    # check if artist exists
    tempCommand = "select %s from %s where %s=\"%s\"" % (wanted_item, from_table, compare_item, compare_value)
    connector._mycursor.execute(tempCommand)
    # check if artist exists
    return connector._mycursor.fetchall()

connector = MySQLConnector()
csv_songsNumbers_dict = {}
genre_dict = {}
line_count = 0
command = "select * from genre"
connector._mycursor.execute(command)
genre_list = connector._mycursor.fetchall()
for g in genre_list:
    genre_dict.update({g[1] : g[0]})

with open("C:\Users\NadavSpitzer\Desktop\SongCSV.csv") as tables:
    csv_reader = csv.reader(tables, delimiter=',')
    temp_album_number = 0
    temp_artist_number = 0
    song_number = 1
    album_number = 1
    artist_number = 1
    genre_number = 1
    for row in csv_reader:
        if line_count == 0:
            line_count = line_count+1
            continue
        song_id = song_number
        album_id = int(row[2])
        album_name = str(row[3])
        artist_id = row[4]
        artist_hotness = float(row[5])
        artist_familiarity = float(row[6])
        artist_name = str(row[10])
        dancibility = float(row[11])
        duration = float(row[12])
        tempo = float(row[15])
        song_name = str(row[18])
        song_hotness = float(row[19])
        loudness = float(row[20])
        year = int(row[22])
        if year == 0:
            # if year is 0, randomize a year
            year = random.randint(1919, 2018)
        similar_artists = str(row[23])
        genre = str(row[24]).split(',')
        csv_songsNumbers_dict.update({(artist_name, song_name) : song_number})
        song_number += 1

"""
        songCommand = "update song set year=%d where song_id=%d" % (year, song_number)
        connector._mycursor.execute(songCommand)
        connector.commit()
        inserted_genre = False
        artistCommand = "select artist.artist_id from artist, artist_album, album, album_song, song " \
                        "where artist.artist_id=artist_album.artist_id and " \
                        " artist_album.album_id=album.album_id and " \
                        "album.album_id=album_song.album_id and " \
                        "album_song.song_id = song.song_id and " \
                        "artist.artist_name=\"%s\" and song.name=\"%s\"" \
                        % (artist_name.replace("\"","").replace("\'",""), song_name.replace("\"","").replace("\'",""))
        connector._mycursor.execute(artistCommand)
        checkArtist = connector._mycursor.fetchall()
        if len(checkArtist) is not 0:
            checkArtist = checkArtist[0][0]
            for subGenre in genre:
                temp_dict = genre_dict.copy()
                for g in temp_dict.copy():
                    if g in subGenre:
                        inserted_genre = True
                        command = "insert into artist_genre(artist_id, genre_id)values(\"%s\", %d)" % (checkArtist, genre_dict[g])
                        connector._mycursor.execute(command)
                        connector.commit()
                        temp_dict.pop(g)
                    if inserted_genre is False:
                        command = "insert into artist_genre(artist_id, genre_id)values(\"other\", 29)"
                        connector._mycursor.execute(command)
                        connector.commit()
        print "Song %d was inserted " % (song_number)
        song_number += 1"""

with open("C:\Users\NadavSpitzer\Desktop\songdata.csv") as lyrics:
    csv_reader = csv.reader(lyrics, delimiter=',')
    line_count = 0
    for row in csv_reader:
        if line_count == 0:
            line_count = line_count+1
            continue
        artistName = row[0]
        songName = row[1]
        words = str(row[2]).replace("\"", "")
        songId = csv_songsNumbers_dict.get((artistName, songName))
        if songId is not None:
            command = "update song set words=\"%s\" where song_id=%d" % (words, songId)
            connector._mycursor.execute(command)
            print "Words of song %d inserted." % (songId)
            line_count += 1
print "%d words of songs added." % line_count
connector.commit()
