import {Genre} from "./Film";

export interface NewFilm {
  id: number | null,
  title: string,
  year: number,
  genres: Genre[],
  reviews: NewReview[]
}

export interface NewReview {
  id: number | null,
  date: string,
  rating: number,
  detail: string | null,
  filmId: number
}