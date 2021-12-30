import { Protocol } from '../model/Protocol';
import { FilmReviewRepository, filmReviewRepository } from './FilmReviewRepository';

export const filmReviewProtocolRepository: Record<Protocol, FilmReviewRepository> = {
  [Protocol.JPA]: filmReviewRepository(Protocol.JPA),
  [Protocol.JDBC]: filmReviewRepository(Protocol.JDBC),
  [Protocol.XLSX]: filmReviewRepository(Protocol.XLSX),
};