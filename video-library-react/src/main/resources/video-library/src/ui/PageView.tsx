import React, { useState } from 'react';
import { Film } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { filmProtocolRepository } from '../repository/ProtocolRepository';
import { FilmListView } from './FilmListView';
import { FilmView } from './FilmView';

type Props = {}

export const PageView: React.FC<Props> = () => {
  const [selectedDbProtocol, setSelectedDbProtocol] = useState<Protocol>(Protocol.JPA);
  const [films, setFilms] = useState<Film[]>([]);
  const [jsonBody, setJsonBody] = useState('');
  const [selectedFilm, setSelectedFilm] = useState<Film>();
  const filmRepository = filmProtocolRepository[selectedDbProtocol];

  const retrieveFilms = (): Promise<Film[]> => {
    return filmRepository.get();
  };

  const updateFilms = (): Promise<Film[]> => {
    return filmRepository.insert(films);
  };

  const onRetrieveFilmClick = (): void => {
    retrieveFilms()
      .then((films) => {
        setFilms(films);
        setJsonBody(JSON.stringify(films, null, 4));
      });
  };

  const onUpdateFilmClick = (): void => {
    updateFilms()
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
            Protocol.values.map((protocol) =>
              <option key={protocol} value={protocol}>{protocol}</option>
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