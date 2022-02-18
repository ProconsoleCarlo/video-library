import { Film } from '../model/Film';
import { Protocol } from '../model/Protocol';
import { fetchHttpClient } from '../utils/HttpClient';
import { ByProtocolRepository } from './ByProtocolRepository';

export interface FilmRepository extends ByProtocolRepository {
  get(): Promise<Film[]>;

  update(films: Film[]): Promise<Film[]>;

  insert(films: Film[]): Promise<Film[]>;
}

const httpClient = fetchHttpClient<Film[] | void, Film[]>();

export const filmRepository = (protocol: Protocol): FilmRepository => {
  return {
    protocol(): Protocol {
      return protocol;
    },
    get(): Promise<Film[]> {
      return httpClient.get({url: `/${protocol}/films`});
    },
    update(films: Film[]): Promise<Film[]> {
      return httpClient.post({url: `/${protocol}/films`, body: films});
    },
    insert(films: Film[]): Promise<Film[]> {
      return httpClient.put({url: `/${protocol}/films`, body: films});
    }
  };
};