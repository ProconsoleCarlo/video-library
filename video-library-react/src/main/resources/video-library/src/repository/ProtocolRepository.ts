import { Protocol } from '../model/Protocol';
import { filmRepository, FilmRepository } from './FilmRepository';
import { FilmReviewRepository, filmReviewRepository } from './FilmReviewRepository';

export const filmReviewProtocolRepository: Record<Protocol, FilmReviewRepository> = {
  [Protocol.JPA]: filmReviewRepository(Protocol.JPA),
  [Protocol.JDBC]: filmReviewRepository(Protocol.JDBC),
  [Protocol.XLSX]: filmReviewRepository(Protocol.XLSX),
};

export const filmProtocolRepository: Record<Protocol, FilmRepository> = {
  [Protocol.JPA]: filmRepository(Protocol.JPA),
  [Protocol.JDBC]: filmRepository(Protocol.JDBC),
  [Protocol.XLSX]: filmRepository(Protocol.XLSX),
};