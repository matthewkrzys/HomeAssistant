import ShoppingListView from "../views/ShoppingListView";
import { ViewType } from "../App";
import AddShoppingItem from "../views/AddShoppingItem";

interface ContentProps {
  view: ViewType;
}

const Content = ({ view }: ContentProps) => {
  switch (view) {
    case "shopping":
      return <ShoppingListView />;
    case "addShopping":
      return <AddShoppingItem />;
    default:
      return null;
  }
};

export default Content;
