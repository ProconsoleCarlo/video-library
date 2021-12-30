import { Film } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { fetchHttpClient } from '../utils/HttpClient';

export interface FilmRepository {
  get(): Promise<Film[]>;

  update(films: Film[]): Promise<Film[]>;

  insert(films: Film[]): Promise<Film[]>;
}

const httpClient = fetchHttpClient<string, Film[]>();

export const filmRepository = (protocol: Protocol): FilmRepository => {
  return {
    get(): Promise<Film[]> {
      return httpClient.get({url: `/${protocol}/films`});
    },
    update(films: Film[]): Promise<Film[]> {
      return httpClient.post({url: `/${protocol}/films`, body: JSON.stringify(films)});
    },
    insert(films: Film[]): Promise<Film[]> {
      return httpClient.put({url: `/${protocol}/films`, body: JSON.stringify(films)});
    }
  };
};