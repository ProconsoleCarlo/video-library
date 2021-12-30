import React, { useState } from 'react';
import { Film } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { fetchHttpClient } from '../utils/HttpClient';
import { FilmListView } from './FilmListView';
import { FilmView } from './FilmView';

const httpClient = fetchHttpClient<any, any>();

const dbProtocols = ['jpa', 'jdbc'];

interface Props {
}

export const PageView: React.FC<Props> = () => {
  const [selectedDbProtocol, setSelectedDbProtocol] = useState(Protocol.JPA);
  const [films, setFilms] = useState<Film[]>([]);
  const [jsonBody, setJsonBody] = useState('');
  const [selectedFilm, setSelectedFilm] = useState<Film>();

  const retrieveFilms = (dbProtocol: string): Promise<Film[]> => {
    return httpClient.get({url: `/${dbProtocol}/films`});
  };

  const updateFilms = (dbProtocol: string): Promise<Film[]> => {
    return httpClient.put({url: `/${dbProtocol}/films`, body: jsonBody});
  };

  const onRetrieveFilmClick = (): void => {
    retrieveFilms(selectedDbProtocol.toLowerCase())
      .then((films) => {
        setFilms(films);
        setJsonBody(JSON.stringify(films, null, 4));
      });
  };

  const onUpdateFilmClick = (): void => {
    updateFilms(selectedDbProtocol.toLowerCase())
      .then((films) => {
        setFilms(films);
      });
  };

  const onJsonChange = (value: string): void => {
    setJsonBody(value);
  };

  const onFilmClick = (film: Film): void => {
    setSelectedFilm(film);
  };

  return (
    <div>
      <div>
        <select value={selectedDbProtocol} onChange={(e): void => setSelectedDbProtocol(e.target.value as Protocol)}>
          {
            dbProtocols.map((dbProtocol) =>
              <option key={dbProtocol} value={dbProtocol}>{dbProtocol}</option>
            )
          }
        </select>
        <button onClick={onRetrieveFilmClick}>Retrieve films</button>
      </div>
      <FilmListView films={films} onFilmClick={onFilmClick}/>
      {
        selectedFilm && <FilmView film={selectedFilm} protocol={selectedDbProtocol}/>
      }
      <div>
        <span>IO text area:</span><br/>
        <textarea rows={12}
                  cols={200}
                  value={jsonBody}
                  onChange={e => onJsonChange(e.target.value)}
        /><br/>
        <button onClick={onUpdateFilmClick}>Update films</button>
      </div>
    </div>
  );
};