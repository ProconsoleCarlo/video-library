export type Film = {
  id: number | null,
  title: string,
  year: number,
  genres: Genre[],
  reviews: Review[]
}

export type Genre = {
  id: number,
  value: string
}

export type Review = {
  id: number | null,
  date: string,
  rating: number,
  detail: string | null
}