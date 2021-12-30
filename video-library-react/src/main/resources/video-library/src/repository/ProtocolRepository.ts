import { JDBC, JPA, XLSX } from '../model/Protocols';
import { filmReviewRepository } from './FilmReviewRepository';

export const filmReviewRepositories = new Map([
	[JPA, filmReviewRepository(JPA)],
	[JDBC, filmReviewRepository(JDBC)],
	[XLSX, filmReviewRepository(XLSX)],
]);